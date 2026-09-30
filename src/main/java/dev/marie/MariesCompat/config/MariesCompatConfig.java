package dev.marie.MariesCompat.config;

import dev.marie.MariesCompat.compat.saturation.SaturationDisplayMode;
import dev.marie.MariesCompat.core.MariesCompat;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class MariesCompatConfig {

    private static MariesCompatConfig INSTANCE;
    private static ModConfigSpec SPEC;
    private static volatile ModConfig boundCommonConfig;

    // Peak Stamina
    private final ModConfigSpec.BooleanValue enablePSStaminaUsage;
    private final ModConfigSpec.BooleanValue enablePSPenaltyDecay;
    private final ModConfigSpec.BooleanValue enablePSExhaustionDuration;

    // Spice of Life: Onion
    private final ModConfigSpec.BooleanValue enableSOLDiversityHealth;
    private final ModConfigSpec.BooleanValue enableSOLDiversityPenalty;

    // Legendary Survival Overhaul
    private final ModConfigSpec.BooleanValue enableLSOThermalResistance;
    private final ModConfigSpec.BooleanValue enableLSOBrokenHeartResilience;
    private final ModConfigSpec.BooleanValue enableLSOThirstSaturation;

    // Saturation Tweaks (standalone, no external mod required)
    private final ModConfigSpec.BooleanValue enableSaturationTweaks;
    private final ModConfigSpec.DoubleValue maxExhaustion;
    private final ModConfigSpec.IntValue saturationCap;
    private final ModConfigSpec.BooleanValue hungerLimitsSaturation;
    private final ModConfigSpec.BooleanValue alwaysCanEat;

    // Saturation Display (independent of the mechanic tweaks above — client-side rendering only)
    private final ModConfigSpec.EnumValue<SaturationDisplayMode> saturationDisplayMode;
    private final ModConfigSpec.BooleanValue exhaustionDisplayed;

    private MariesCompatConfig(ModConfigSpec.Builder builder) {
        builder.push("peakStamina");
        enablePSStaminaUsage = builder.define("enablePSStaminaUsage", true);
        enablePSPenaltyDecay = builder.define("enablePSPenaltyDecay", true);
        enablePSExhaustionDuration = builder.define("enablePSExhaustionDuration", true);
        builder.pop();

        builder.push("spiceOfLifeOnion");
        enableSOLDiversityHealth = builder.define("enableSOLDiversityHealth", true);
        enableSOLDiversityPenalty = builder.define("enableSOLDiversityPenalty", true);
        builder.pop();

        builder.push("legendarysurvivaloverhaul");
        enableLSOThermalResistance = builder.define("enableLSOThermalResistance", true);
        enableLSOBrokenHeartResilience = builder.define("enableLSOBrokenHeartResilience", true);
        enableLSOThirstSaturation = builder.define("enableLSOThirstSaturation", true);
        builder.pop();

        builder.push("saturationTweaks");
        enableSaturationTweaks = builder
                .comment("Master toggle. Off by default so installing this mod never changes hunger mechanics unless you opt in.")
                .define("enableSaturationTweaks", false);
        maxExhaustion = builder
                .comment("Exhaustion level that must be reached before hunger/saturation drains. Vanilla default is 4.0 — raise it to make food last longer.")
                .defineInRange("maxExhaustion", 4.0, 0.1, 1000.0);
        saturationCap = builder
                .comment("Maximum saturation level. -1 disables the cap (vanilla behavior).")
                .defineInRange("saturationCap", -1, -1, Integer.MAX_VALUE);
        hungerLimitsSaturation = builder
                .comment("If true, saturation can never exceed your current food level.")
                .define("hungerLimitsSaturation", false);
        alwaysCanEat = builder
                .comment("If true, you can always eat regardless of food level, like Hunger Games/peaceful-style rules.")
                .define("alwaysCanEat", false);
        builder.pop();

        builder.push("saturationDisplay");
        saturationDisplayMode = builder
                .comment("ALWAYS: always show the raw saturation number. BEYOND_MAX: only show it once saturation exceeds 20 (past what AppleSkin's hunger-bar overlay can visually represent). NEVER: don't show it.")
                .defineEnum("saturationDisplayMode", SaturationDisplayMode.NEVER);
        exhaustionDisplayed = builder
                .comment("Shows exhaustion as a percentage of the threshold needed to drain hunger/saturation.")
                .define("exhaustionDisplayed", false);
        builder.pop();
    }

    public static void register(ModContainer modContainer) {
        if (INSTANCE != null) return;
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        INSTANCE = new MariesCompatConfig(builder);
        SPEC = builder.build();
        modContainer.registerConfig(ModConfig.Type.COMMON, SPEC);
    }

    public static void onModConfigLoading(ModConfigEvent.Loading event) {
        ModConfig cfg = event.getConfig();
        if (!MariesCompat.MODID.equals(cfg.getModId()) || cfg.getType() != ModConfig.Type.COMMON) return;
        if (cfg.getSpec() != SPEC) return;
        boundCommonConfig = cfg;
        syncModuleCache();
    }

    public static void onModConfigReloading(ModConfigEvent.Reloading event) {
        ModConfig cfg = event.getConfig();
        if (!MariesCompat.MODID.equals(cfg.getModId()) || cfg.getType() != ModConfig.Type.COMMON) return;
        if (cfg.getSpec() != SPEC) return;
        syncModuleCache();
    }

    public static void saveNow() {
        ModConfig cfg = boundCommonConfig;
        if (cfg == null) return;
        var loaded = cfg.getLoadedConfig();
        if (loaded != null) loaded.save();
        syncModuleCache();
    }

    public static void syncModuleCache() {
        if (INSTANCE == null) return;
        MariesCompatModuleCache.refresh(INSTANCE);
    }

    public static MariesCompatConfig get() {
        if (INSTANCE == null) throw new IllegalStateException("MariesCompatConfig has not been registered yet.");
        return INSTANCE;
    }

    public static ModConfigSpec spec() { return SPEC; }

    // Getters
    public boolean enablePSStaminaUsage() { return enablePSStaminaUsage.get(); }
    public boolean enablePSPenaltyDecay() { return enablePSPenaltyDecay.get(); }
    public boolean enablePSExhaustionDuration() { return enablePSExhaustionDuration.get(); }
    public boolean enableSOLDiversityHealth() { return enableSOLDiversityHealth.get(); }
    public boolean enableSOLDiversityPenalty() { return enableSOLDiversityPenalty.get(); }
    public boolean enableLSOThermalResistance() { return enableLSOThermalResistance.get(); }
    public boolean enableLSOBrokenHeartResilience() { return enableLSOBrokenHeartResilience.get(); }
    public boolean enableLSOThirstSaturation() { return enableLSOThirstSaturation.get(); }
    public boolean enableSaturationTweaks() { return enableSaturationTweaks.get(); }
    public double maxExhaustion() { return maxExhaustion.get(); }
    public int saturationCap() { return saturationCap.get(); }
    public boolean hungerLimitsSaturation() { return hungerLimitsSaturation.get(); }
    public boolean alwaysCanEat() { return alwaysCanEat.get(); }
    public SaturationDisplayMode saturationDisplayMode() { return saturationDisplayMode.get(); }
    public boolean exhaustionDisplayed() { return exhaustionDisplayed.get(); }

    // Setters (used by config screen save consumers)
    public void setEnablePSStaminaUsage(boolean v) { enablePSStaminaUsage.set(v); }
    public void setEnablePSPenaltyDecay(boolean v) { enablePSPenaltyDecay.set(v); }
    public void setEnablePSExhaustionDuration(boolean v) { enablePSExhaustionDuration.set(v); }
    public void setEnableSOLDiversityHealth(boolean v) { enableSOLDiversityHealth.set(v); }
    public void setEnableSOLDiversityPenalty(boolean v) { enableSOLDiversityPenalty.set(v); }
    public void setEnableLSOThermalResistance(boolean v) { enableLSOThermalResistance.set(v); }
    public void setEnableLSOBrokenHeartResilience(boolean v) { enableLSOBrokenHeartResilience.set(v); }
    public void setEnableLSOThirstSaturation(boolean v) { enableLSOThirstSaturation.set(v); }
    public void setEnableSaturationTweaks(boolean v) { enableSaturationTweaks.set(v); }
    public void setMaxExhaustion(double v) { maxExhaustion.set(v); }
    public void setSaturationCap(int v) { saturationCap.set(v); }
    public void setHungerLimitsSaturation(boolean v) { hungerLimitsSaturation.set(v); }
    public void setAlwaysCanEat(boolean v) { alwaysCanEat.set(v); }
    public void setSaturationDisplayMode(SaturationDisplayMode v) { saturationDisplayMode.set(v); }
    public void setExhaustionDisplayed(boolean v) { exhaustionDisplayed.set(v); }
}
