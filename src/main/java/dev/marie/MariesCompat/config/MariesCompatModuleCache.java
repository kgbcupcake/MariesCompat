package dev.marie.MariesCompat.config;

public final class MariesCompatModuleCache {

    public static boolean enablePSStaminaUsage = true;
    public static boolean enablePSPenaltyDecay = true;
    public static boolean enablePSExhaustionDuration = true;
    public static boolean enableSOLDiversityHealth = true;
    public static boolean enableSOLDiversityPenalty = true;
    public static boolean enableLSOThermalResistance = true;
    public static boolean enableLSOBrokenHeartResilience = true;
    public static boolean enableLSOThirstSaturation = true;

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
    }
}
