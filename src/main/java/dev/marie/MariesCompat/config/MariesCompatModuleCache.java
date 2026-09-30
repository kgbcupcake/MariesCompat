package dev.marie.MariesCompat.config;

import dev.marie.MariesCompat.compat.saturation.SaturationDisplayMode;

public final class MariesCompatModuleCache {

    public static boolean enablePSStaminaUsage = true;
    public static boolean enablePSPenaltyDecay = true;
    public static boolean enablePSExhaustionDuration = true;
    public static boolean enableSOLDiversityHealth = true;
    public static boolean enableSOLDiversityPenalty = true;
    public static boolean enableLSOThermalResistance = true;
    public static boolean enableLSOBrokenHeartResilience = true;
    public static boolean enableLSOThirstSaturation = true;

    public static boolean enableSaturationTweaks = false;
    public static float maxExhaustion = 4.0F;
    public static int saturationCap = -1;
    public static boolean hungerLimitsSaturation = false;
    public static boolean alwaysCanEat = false;

    public static SaturationDisplayMode saturationDisplayMode = SaturationDisplayMode.NEVER;
    public static boolean exhaustionDisplayed = false;

    private MariesCompatModuleCache() {}

    public static void refresh(MariesCompatConfig c) {
        enablePSStaminaUsage = c.enablePSStaminaUsage();
        enablePSPenaltyDecay = c.enablePSPenaltyDecay();
        enablePSExhaustionDuration = c.enablePSExhaustionDuration();
        enableSOLDiversityHealth = c.enableSOLDiversityHealth();
        enableSOLDiversityPenalty = c.enableSOLDiversityPenalty();
        enableLSOThermalResistance = c.enableLSOThermalResistance();
        enableLSOBrokenHeartResilience = c.enableLSOBrokenHeartResilience();
        enableLSOThirstSaturation = c.enableLSOThirstSaturation();
        enableSaturationTweaks = c.enableSaturationTweaks();
        maxExhaustion = (float) c.maxExhaustion();
        saturationCap = c.saturationCap();
        hungerLimitsSaturation = c.hungerLimitsSaturation();
        alwaysCanEat = c.alwaysCanEat();
        saturationDisplayMode = c.saturationDisplayMode();
        exhaustionDisplayed = c.exhaustionDisplayed();
    }
}
