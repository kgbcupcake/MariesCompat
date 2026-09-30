package dev.marie.MariesCompat.compat.saturation.mixin;

import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FoodData.class)
public interface FoodDataAccessor {

    @Accessor
    int getFoodLevel();

    @Accessor("foodLevel")
    void setFoodLevel(int value);

    @Accessor
    float getSaturationLevel();

    @Accessor("saturationLevel")
    void setSaturationLevel(float value);

    @Accessor
    float getExhaustionLevel();

    @Accessor("exhaustionLevel")
    void setExhaustionLevel(float value);
}
