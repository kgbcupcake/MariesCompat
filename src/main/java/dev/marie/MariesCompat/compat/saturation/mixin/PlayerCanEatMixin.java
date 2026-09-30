package dev.marie.MariesCompat.compat.saturation.mixin;

import dev.marie.MariesCompat.config.MariesCompatModuleCache;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerCanEatMixin {

    @Inject(method = "canEat(Z)Z", at = @At("HEAD"), cancellable = true)
    private void mariescompat$alwaysCanEat(boolean ignoreHunger, CallbackInfoReturnable<Boolean> cir) {
        if (MariesCompatModuleCache.enableSaturationTweaks && MariesCompatModuleCache.alwaysCanEat) {
            cir.setReturnValue(true);
        }
    }
}
