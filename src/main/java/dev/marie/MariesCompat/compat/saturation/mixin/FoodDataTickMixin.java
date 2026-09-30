package dev.marie.MariesCompat.compat.saturation.mixin;

import dev.marie.MariesCompat.config.MariesCompatModuleCache;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Applies the configurable saturation cap and hunger-limits-saturation clamp after vanilla's own
 * tick has run, so normal hunger/saturation decay always behaves correctly and this only ever
 * tightens the result — never replaces vanilla's decay logic outright.
 */
@Mixin(FoodData.class)
public class FoodDataTickMixin {

    @Inject(method = "tick(Lnet/minecraft/world/entity/player/Player;)V", at = @At("TAIL"))
    private void mariescompat$applyCaps(Player player, CallbackInfo ci) {
        if (!MariesCompatModuleCache.enableSaturationTweaks) {
            return;
        }

        FoodDataAccessor accessor = (FoodDataAccessor) this;
        float saturationLevel = accessor.getSaturationLevel();

        if (MariesCompatModuleCache.hungerLimitsSaturation) {
            saturationLevel = Math.min(saturationLevel, (float) accessor.getFoodLevel());
        }

        int cap = MariesCompatModuleCache.saturationCap;
        if (cap >= 0) {
            saturationLevel = Math.min(saturationLevel, (float) cap);
        }

        accessor.setSaturationLevel(Math.max(0.0F, saturationLevel));
    }
}
