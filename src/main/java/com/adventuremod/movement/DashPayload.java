package com.adventuremod.movement;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record DashPayload(boolean active) implements CustomPayload {
    public static final CustomPayload.Id<DashPayload> ID = new CustomPayload.Id<>(Identifier.of("adventuremod", "dash"));
    public static final PacketCodec<PacketByteBuf, DashPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, DashPayload::active,
            DashPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
