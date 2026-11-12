package de.mcrising.lightningpowers.client;

import de.mcrising.lightningpowers.config.PowerConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import java.util.ArrayList;

public final class PowerConfigScreen {
    private PowerConfigScreen() {}
    public static Screen create(Screen parent) {
        PowerConfig c = PowerConfig.INSTANCE;
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent)
                .setTitle(Component.translatable("config.lightningpowers.title"));
        ConfigEntryBuilder e = builder.entryBuilder();
        ConfigCategory basic = builder.getOrCreateCategory(Component.translatable("config.lightningpowers.basic"));
        basic.addEntry(e.startBooleanToggle(label("enabled"), c.enabled).setDefaultValue(true).setSaveConsumer(v -> c.enabled=v).build());
        basic.addEntry(e.startIntField(label("duration"), c.durationSeconds).setDefaultValue(60).setMin(1).setMax(86400).setSaveConsumer(v -> c.durationSeconds=v).build());
        basic.addEntry(e.startIntField(label("cooldown"), c.cooldownSeconds).setDefaultValue(120).setMin(0).setMax(86400).setSaveConsumer(v -> c.cooldownSeconds=v).build());
        basic.addEntry(e.startBooleanToggle(label("recharge"), c.reactivationResetsDuration).setDefaultValue(true).setSaveConsumer(v -> c.reactivationResetsDuration=v).build());

        ConfigCategory stats = builder.getOrCreateCategory(Component.translatable("config.lightningpowers.stats"));
        stats.addEntry(e.startBooleanToggle(label("heal"), c.refillHealth).setDefaultValue(true).setSaveConsumer(v -> c.refillHealth=v).build());
        stats.addEntry(e.startBooleanToggle(label("food"), c.refillFood).setDefaultValue(true).setSaveConsumer(v -> c.refillFood=v).build());
        stats.addEntry(e.startIntField(label("foodlevel"), c.foodLevel).setDefaultValue(20).setMin(0).setMax(20).setSaveConsumer(v -> c.foodLevel=v).build());
        stats.addEntry(e.startFloatField(label("saturation"), c.saturation).setDefaultValue(20f).setMin(0f).setMax(20f).setSaveConsumer(v -> c.saturation=v).build());
        stats.addEntry(e.startStrList(label("effects"), new ArrayList<>(c.effects)).setSaveConsumer(v -> c.effects = new ArrayList<>(v)).build());
        stats.addEntry(e.startBooleanToggle(label("effectsactive"), c.applyEffectsDuringCharge).setDefaultValue(true).setSaveConsumer(v -> c.applyEffectsDuringCharge=v).build());

        ConfigCategory combat = builder.getOrCreateCategory(Component.translatable("config.lightningpowers.combat"));
        combat.addEntry(e.startBooleanToggle(label("lightningimmune"), c.preventLightningDamage).setDefaultValue(true).setSaveConsumer(v -> c.preventLightningDamage=v).build());
        combat.addEntry(e.startBooleanToggle(label("playerfire"), c.preventPlayerFire).setDefaultValue(true).setSaveConsumer(v -> c.preventPlayerFire=v).build());
        combat.addEntry(e.startBooleanToggle(label("electricmelee"), c.electricMelee).setDefaultValue(true).setSaveConsumer(v -> c.electricMelee=v).build());
        combat.addEntry(e.startFloatField(label("damage"), c.electricBonusDamage).setDefaultValue(5f).setMin(0f).setMax(1000f).setSaveConsumer(v -> c.electricBonusDamage=v).build());
        combat.addEntry(e.startBooleanToggle(label("particles"), c.electricParticles).setDefaultValue(true).setSaveConsumer(v -> c.electricParticles=v).build());
        combat.addEntry(e.startBooleanToggle(label("helmetdamage"), c.damageHelmet).setDefaultValue(true).setSaveConsumer(v -> c.damageHelmet=v).build());
        combat.addEntry(e.startIntField(label("wear"), c.helmetDamagePerStrike).setDefaultValue(25).setMin(0).setMax(10000).setSaveConsumer(v -> c.helmetDamagePerStrike=v).build());
        builder.setSavingRunnable(PowerConfig::save);
        return builder.build();
    }
    private static Component label(String key) { return Component.translatable("config.lightningpowers."+key); }
}
