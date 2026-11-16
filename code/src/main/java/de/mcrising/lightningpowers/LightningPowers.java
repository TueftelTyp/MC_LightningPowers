package de.mcrising.lightningpowers;

import de.mcrising.lightningpowers.config.PowerConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.Level;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LightningPowers implements ModInitializer {
    public static final String MOD_ID = "lightningpowers";
    private static final Identifier HELMET_ID = Identifier.parse(MOD_ID + ":lightning_helmet");
    private static final Identifier CHARGED_HELMET_MODEL = Identifier.parse(MOD_ID + ":lightning_helmet_charged");
    // True humanoid armor: unlike a plain equippable item, this participates in
    // Minecraft's armor rendering pipeline and in armor equipment behavior.
    private static final ResourceKey<EquipmentAsset> HELMET_ASSET = ResourceKey.create(
        EquipmentAssets.ROOT_ID, Identifier.parse(MOD_ID + ":lightning_helmet"));
    private static final TagKey<Item> HELMET_REPAIRS = TagKey.create(
        BuiltInRegistries.ITEM.key(), Identifier.parse("lightningpowers:repairs_lightning_helmet"));
    private static final ArmorMaterial HELMET_MATERIAL = new ArmorMaterial(
        24, Map.of(ArmorType.HELMET, 3), 12,
        SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F,
        HELMET_REPAIRS, HELMET_ASSET);
    public static Item HELMET;
    private static final Map<UUID, Charge> ACTIVE = new HashMap<>();
    private static final Map<UUID, Long> COOLDOWNS = new HashMap<>();
    // Fire from a lightning bolt is placed in the world separately from thunderHit.
    // Re-check the impact area for a few ticks to remove only fire blocks next to the wearer.
    private static final Map<UUID, Long> LIGHTNING_FIRE_CLEANUP = new HashMap<>();
    private record Charge(long untilTick) {}
    private static long tick = 0;

    @Override public void onInitialize() {
        PowerConfig.load();
        HELMET = Registry.register(BuiltInRegistries.ITEM, HELMET_ID,
           new Item(new Item.Properties()
             .setId(ResourceKey.create(Registries.ITEM, HELMET_ID))
             .stacksTo(1)
             .humanoidArmor(HELMET_MATERIAL, ArmorType.HELMET)
             .durability(384)
             .component(DataComponents.ITEM_MODEL, HELMET_ID)));
        // Both the Combat category and the search results need explicit entries.
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT)
            .register(tab -> tab.accept(HELMET));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SEARCH)
            .register(tab -> tab.accept(HELMET));
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tick++;
            // Fire protection must run EVERY tick, not just on the one-second
            // effect refresh. Otherwise the player can visibly burn in between.
            if (PowerConfig.INSTANCE.enabled && PowerConfig.INSTANCE.preventPlayerFire) {
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    if (wearingHelmet(player)) {
                        // Remove the burning status during active charging or a recent strike.
                        Long cleanupUntil = LIGHTNING_FIRE_CLEANUP.get(player.getUUID());
                        boolean recentStrike = cleanupUntil != null && tick <= cleanupUntil;
                        if (isCharged(player) || recentStrike) player.clearFire();
                        if (recentStrike) removeStrikeFire(player);
                    }
                }
            }
            LIGHTNING_FIRE_CLEANUP.entrySet().removeIf(entry -> tick > entry.getValue());
            if ((tick % 20) == 0) {
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    Charge charge = ACTIVE.get(player.getUUID());
                    boolean charged = charge != null && tick < charge.untilTick() && player.isAlive();
                    if (!charged && charge != null) ACTIVE.remove(player.getUUID());
                    // Sync the visible helmet state with the current power state.
                    ItemStack equipped = player.getItemBySlot(EquipmentSlot.HEAD);
                    if (equipped.is(HELMET)) {
                        Identifier desired = charged ? CHARGED_HELMET_MODEL : HELMET_ID;
                        if (!desired.equals(equipped.get(DataComponents.ITEM_MODEL)))
                            equipped.set(DataComponents.ITEM_MODEL, desired);
                    }
                    if (!charged) continue;
                    if (PowerConfig.INSTANCE.applyEffectsDuringCharge) applyEffects(player);
                    if (PowerConfig.INSTANCE.electricParticles) {
                        ((net.minecraft.server.level.ServerLevel) player.level()).sendParticles(
                            ParticleTypes.ELECTRIC_SPARK, player.getX(), player.getY() + 1, player.getZ(),
                            16, .4, .7, .4, .05);
                    }
                }
            }
        });
    }

    private static boolean wearingHelmet(ServerPlayer player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(HELMET);
    }

    private static void removeStrikeFire(ServerPlayer player) {
        // Only remove ordinary fire in the 3x3 area immediately around the struck player.
        // No terrain blocks, lava, or more distant fires are altered.
        BlockPos origin = player.blockPosition();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos pos = origin.offset(dx, dy, dz);
                    if (player.level().getBlockState(pos).is(Blocks.FIRE)) {
                        player.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    }
                }
            }
        }
    }

    public static boolean onLightning(ServerPlayer player) {
        PowerConfig cfg = PowerConfig.INSTANCE;
        if (!cfg.enabled || !wearingHelmet(player)) return false;
        // Even during cooldown, an equipped helmet protects its wearer from
        // being ignited by subsequent lightning strikes.
        if (cfg.preventPlayerFire) {
            player.clearFire();
            // The bolt can generate fire after the thunderHit callback returns.
            // Clean the immediate impact area for 12 server ticks.
            LIGHTNING_FIRE_CLEANUP.put(player.getUUID(), tick + 12L);
            removeStrikeFire(player);
        }
        if (tick < COOLDOWNS.getOrDefault(player.getUUID(), 0L)) return cfg.preventLightningDamage;
        if (!cfg.reactivationResetsDuration && ACTIVE.containsKey(player.getUUID()) && isCharged(player))
            return cfg.preventLightningDamage;
        if (cfg.refillHealth) player.setHealth(player.getMaxHealth());
        if (cfg.refillFood) {
            player.getFoodData().setFoodLevel(cfg.foodLevel);
            player.getFoodData().setSaturation(cfg.saturation);
        }
        long duration = (long) cfg.durationSeconds * 20;
        ACTIVE.put(player.getUUID(), new Charge(tick + duration));
        COOLDOWNS.put(player.getUUID(), tick + (long) cfg.cooldownSeconds * 20);
        // Update immediately, without waiting for the one-second heartbeat.
        ItemStack worn = player.getItemBySlot(EquipmentSlot.HEAD);
        worn.set(DataComponents.ITEM_MODEL, CHARGED_HELMET_MODEL);
        applyEffects(player);
        if (cfg.preventPlayerFire) player.clearFire();
        if (cfg.damageHelmet && cfg.helmetDamagePerStrike > 0) {
            ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
            helmet.hurtAndBreak(cfg.helmetDamagePerStrike, player, EquipmentSlot.HEAD);
        }
        return cfg.preventLightningDamage;
    }

    public static boolean isCharged(Player player) {
        Charge charge = ACTIVE.get(player.getUUID());
        return charge != null && tick < charge.untilTick() && player.isAlive();
    }

    public static void electricAttack(Player player, net.minecraft.world.entity.Entity target) {
        PowerConfig cfg = PowerConfig.INSTANCE;
        if (!cfg.enabled || !cfg.electricMelee || cfg.electricBonusDamage <= 0 || !isCharged(player)) return;
        if (!(player instanceof ServerPlayer serverPlayer) || !(target instanceof LivingEntity living)) return;
        if (!(player.level() instanceof net.minecraft.server.level.ServerLevel level)) return;
        living.hurtServer(level, player.damageSources().playerAttack(player), cfg.electricBonusDamage);
        if (cfg.electricParticles)
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getY()+target.getBbHeight()/2,
                    target.getZ(), 12, .35, .35, .35, .1);
    }

    private static void applyEffects(ServerPlayer player) {
        PowerConfig cfg = PowerConfig.INSTANCE;
        for (String spec : cfg.effects) {
            try {
                String[] parts = spec.trim().split(":");
                if (parts.length != 3) continue;
                Identifier id = Identifier.parse(parts[0] + ":" + parts[1]);
                int amplifier = Math.max(0, Math.min(255, Integer.parseInt(parts[2])));
                ResourceKey<MobEffect> key = ResourceKey.create(Registries.MOB_EFFECT, id);
                BuiltInRegistries.MOB_EFFECT.get(key).ifPresent(effect ->
                    player.addEffect(new MobEffectInstance(effect, 65, amplifier, false, true, true)));
            } catch (Exception ignored) { /* Invalid list entry is ignored without crashing. */ }
        }
    }
}
