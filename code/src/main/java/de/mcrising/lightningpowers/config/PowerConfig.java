package de.mcrising.lightningpowers.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PowerConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("lightningpowers.json");
    public static PowerConfig INSTANCE = new PowerConfig();

    public boolean enabled = true;
    public int durationSeconds = 60;
    public int cooldownSeconds = 120;
    public boolean refillHealth = true;
    public boolean refillFood = true;
    public int foodLevel = 20;
    public float saturation = 20f;
    public boolean preventLightningDamage = true;
    public boolean preventPlayerFire = true;
    public boolean damageHelmet = true;
    public int helmetDamagePerStrike = 25;
    public boolean electricMelee = true;
    public float electricBonusDamage = 5f;
    public boolean electricParticles = true;
    public boolean applyEffectsDuringCharge = true;
    public boolean reactivationResetsDuration = true;
    public List<String> effects = new ArrayList<>(Arrays.asList(
       "minecraft:strength:2", "minecraft:speed:1", "minecraft:jump_boost:1",
       "minecraft:resistance:1", "minecraft:regeneration:1",
       "minecraft:fire_resistance:0", "minecraft:night_vision:0", "minecraft:haste:1"
    ));

    public void validate() {
        durationSeconds = Math.max(1, Math.min(durationSeconds, 86400));
        cooldownSeconds = Math.max(0, Math.min(cooldownSeconds, 86400));
        foodLevel = Math.max(0, Math.min(foodLevel, 20));
        saturation = Math.max(0, Math.min(saturation, 20));
        helmetDamagePerStrike = Math.max(0, Math.min(helmetDamagePerStrike, 10000));
        electricBonusDamage = Math.max(0, Math.min(electricBonusDamage, 1000));
        if (effects == null) effects = new ArrayList<>();
        effects.removeIf(s -> s == null || s.isBlank());
    }

    public static void load() {
        try {
            if (Files.isRegularFile(PATH)) {
                PowerConfig read = GSON.fromJson(Files.readString(PATH), PowerConfig.class);
                if (read != null) INSTANCE = read;
            }
            INSTANCE.validate();
            save();
        } catch (Exception ex) {
            System.err.println("[Lightning Powers] Invalid config; using defaults: " + ex.getMessage());
            INSTANCE = new PowerConfig();
        }
    }

    public static void save() {
        try {
            INSTANCE.validate();
            Files.createDirectories(PATH.getParent());
            Path temp = PATH.resolveSibling(PATH.getFileName() + ".tmp");
            Files.writeString(temp, GSON.toJson(INSTANCE), StandardCharsets.UTF_8);
            try { Files.move(temp, PATH, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING); }
            catch (java.nio.file.AtomicMoveNotSupportedException ex) { Files.move(temp, PATH, StandardCopyOption.REPLACE_EXISTING); }
        } catch (Exception ex) {
            System.err.println("[Lightning Powers] Cannot save config: " + ex.getMessage());
        }
    }
}
