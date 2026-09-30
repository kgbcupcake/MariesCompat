package dev.marie.MariesCompat.compat.saturation.network;

import dev.marie.MariesCompat.compat.saturation.mixin.FoodDataAccessor;
import dev.marie.MariesCompat.config.MariesCompatModuleCache;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class SaturationDisplayNetworking {

    private SaturationDisplayNetworking() {}

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(SaturationDisplayNetworking::registerPayloads);
        NeoForge.EVENT_BUS.register(Server.class);
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("mariescompat").versioned("1").optional();
        registrar.playToClient(
                ExhaustionSyncPayload.TYPE,
                ExhaustionSyncPayload.STREAM_CODEC,
                ExhaustionSyncPayload::handleClient);
    }

    /** Sends the local player's exhaustion state to their client every server tick — cheap (8 bytes), and lets
     * each client decide independently whether to render it, since the display toggle is a client-side config. */
    private static final class Server {
        @SubscribeEvent
        public static void onPlayerTick(EntityTickEvent.Post event) {
            if (!(event.getEntity() instanceof ServerPlayer player)) {
                return;
            }
            if (!player.connection.hasChannel(ExhaustionSyncPayload.TYPE.id())) {
                return;
            }
            float exhaustion = ((FoodDataAccessor) player.getFoodData()).getExhaustionLevel();
            float maxExhaustion = MariesCompatModuleCache.enableSaturationTweaks
                    ? MariesCompatModuleCache.maxExhaustion
                    : 4.0F;
            PacketDistributor.sendToPlayer(player, new ExhaustionSyncPayload(exhaustion, maxExhaustion));
        }
    }
}
