package com.adventuremod.movement;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.payload.CustomPayload;
import net.minecraft.util.Identifier;

public record DashPayload(boolean active) implements CustomPayload {
    public static final CustomPayload.Id<DashPayload> ID = new CustomPayload.Id<>(Identifier.of("adventuremod", "dash"));
    public static final PacketCodec<RegistryByteBuf, DashPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, DashPayload::active,
            DashPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
