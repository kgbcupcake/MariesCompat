package dev.marie.MariesCompat.compat.saturation.client;

/** Last exhaustion values synced from the server via {@code ExhaustionSyncPayload}. Written on the
 * client network thread, read on the render thread — both are the client main thread in practice,
 * but volatile keeps this safe regardless. */
public final class ClientExhaustionState {

    private static volatile float exhaustionLevel = 0.0F;
    private static volatile float maxExhaustion = 4.0F;

    private ClientExhaustionState() {}

    public static void update(float exhaustion, float max) {
        exhaustionLevel = exhaustion;
        maxExhaustion = max;
    }

    public static float exhaustionLevel() {
        return exhaustionLevel;
    }

    public static float maxExhaustion() {
        return maxExhaustion;
    }
}
