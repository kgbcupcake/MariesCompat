package dev.marie.MariesCompat.compat.saturation.mixin;

import dev.marie.MariesCompat.config.MariesCompatModuleCache;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Replaces vanilla's hardcoded 4.0F exhaustion threshold in {@code FoodData#tick} with the
 * configurable {@link MariesCompatModuleCache#maxExhaustion} value. Left untouched (vanilla 4.0F)
 * whenever the saturation module is disabled, so installing this mod never changes hunger
 * mechanics unless the player opts in via config.
 */
@Mixin(FoodData.class)
public class FoodDataExhaustionMixin {

    @ModifyConstant(
            method = "tick(Lnet/minecraft/world/entity/player/Player;)V",
            constant = @Constant(floatValue = 4.0F, ordinal = 0),
            require = 0
    )
    private float mariescompat$maxExhaustionGet(float vanilla, Player player) {
        return MariesCompatModuleCache.enableSaturationTweaks
                ? MariesCompatModuleCache.maxExhaustion
                : vanilla;
    }

    @ModifyConstant(
            method = "tick(Lnet/minecraft/world/entity/player/Player;)V",
            constant = @Constant(floatValue = 4.0F, ordinal = 1),
            require = 0
    )
    private float mariescompat$maxExhaustionSet(float vanilla, Player player) {
        return MariesCompatModuleCache.enableSaturationTweaks
                ? MariesCompatModuleCache.maxExhaustion
                : vanilla;
    }
}
