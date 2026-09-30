package dev.marie.MariesCompat.compat.saturation.network;

import dev.marie.MariesCompat.compat.saturation.client.ClientExhaustionState;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Server-to-client sync of exhaustion state. Vanilla only replicates food level and saturation to
 * the client (via {@code ClientboundSetHealthPacket}) — exhaustion is server-only, so the
 * saturation-display overlay needs its own tiny packet to know the current exhaustion percentage.
 */
public record ExhaustionSyncPayload(float exhaustionLevel, float maxExhaustion) implements CustomPacketPayload {

    public static final Type<ExhaustionSyncPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("mariescompat", "exhaustion_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ExhaustionSyncPayload> STREAM_CODEC = StreamCodec.of(
            (buf, payload) -> {
                buf.writeFloat(payload.exhaustionLevel());
                buf.writeFloat(payload.maxExhaustion());
            },
            buf -> new ExhaustionSyncPayload(buf.readFloat(), buf.readFloat()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleClient(ExhaustionSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() ->
                ClientExhaustionState.update(payload.exhaustionLevel(), payload.maxExhaustion()));
    }
}
