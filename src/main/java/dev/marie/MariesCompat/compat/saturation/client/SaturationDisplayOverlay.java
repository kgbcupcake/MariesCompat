package dev.marie.MariesCompat.compat.saturation.client;

import dev.marie.MariesCompat.compat.saturation.SaturationDisplayMode;
import dev.marie.MariesCompat.config.MariesCompatModuleCache;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

import java.text.DecimalFormat;

/**
 * Renders the raw saturation number and/or exhaustion percentage next to the hunger bar, per the
 * {@code saturationDisplayMode} / {@code exhaustionDisplayed} config. AppleSkin already shows
 * saturation visually as a golden overlay on the hunger bar, but that overlay caps out at 20 —
 * {@link SaturationDisplayMode#BEYOND_MAX} only prints the number once saturation exceeds what
 * AppleSkin can visually represent.
 */
public final class SaturationDisplayOverlay {

    private static final DecimalFormat SATURATION_FORMAT = new DecimalFormat("0.00");
    private static final DecimalFormat EXHAUSTION_FORMAT = new DecimalFormat("0%");

    /** AppleSkin's saturation overlay on the hunger bar caps at the same 20-point space as the
     * hunger bar itself — past this, the number is the only way to see how much saturation you have. */
    private static final float APPLESKIN_VISUAL_CAP = 20.0F;

    private SaturationDisplayOverlay() {}

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || mc.options.hideGui) {
            return;
        }
        if (!isSurvivalOrAdventure(player)) {
            return;
        }

        SaturationDisplayMode mode = MariesCompatModuleCache.saturationDisplayMode;
        boolean showExhaustion = MariesCompatModuleCache.exhaustionDisplayed;
        if (mode == SaturationDisplayMode.NEVER && !showExhaustion) {
            return;
        }

        float saturation = player.getFoodData().getSaturationLevel();
        boolean showSaturation = switch (mode) {
            case ALWAYS -> true;
            case BEYOND_MAX -> saturation > APPLESKIN_VISUAL_CAP;
            case NEVER -> false;
        };
        if (!showSaturation && !showExhaustion) {
            return;
        }

        int x = mc.getWindow().getGuiScaledWidth() / 2;
        int y = mc.getWindow().getGuiScaledHeight();

        int line = 0;
        if (showSaturation) {
            drawCentered(guiGraphics, mc, SATURATION_FORMAT.format(saturation), x + 92, y - 38 + line * 10, 0xFFFF00);
            line++;
        }
        if (showExhaustion) {
            float maxExhaustion = ClientExhaustionState.maxExhaustion();
            float exhaustion = ClientExhaustionState.exhaustionLevel();
            if (maxExhaustion > 0.0F) {
                float percent = Math.min(1.0F, exhaustion / maxExhaustion);
                drawCentered(guiGraphics, mc, EXHAUSTION_FORMAT.format(percent), x + 92, y - 38 + line * 10, 0x808080);
            }
        }
    }

    private static boolean isSurvivalOrAdventure(Player player) {
        return !player.isCreative() && !player.isSpectator();
    }

    private static void drawCentered(GuiGraphics guiGraphics, Minecraft mc, String text, int x, int y, int color) {
        guiGraphics.drawCenteredString(mc.font, text, x, y, color);
    }
}
