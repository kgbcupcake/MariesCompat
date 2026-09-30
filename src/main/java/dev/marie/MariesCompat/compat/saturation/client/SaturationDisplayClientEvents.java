package dev.marie.MariesCompat.compat.saturation.client;

import dev.marie.MariesCompat.core.MariesCompat;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(modid = MariesCompat.MODID, value = Dist.CLIENT)
public final class SaturationDisplayClientEvents {

    private SaturationDisplayClientEvents() {}

    @SubscribeEvent
    public static void registerOverlay(RegisterGuiLayersEvent event) {
        event.registerAboveAll(
                ResourceLocation.fromNamespaceAndPath(MariesCompat.MODID, "saturation_display"),
                SaturationDisplayOverlay::render);
    }
}
