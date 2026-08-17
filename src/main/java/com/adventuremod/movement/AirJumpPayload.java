package com.adventuremod.movement;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record AirJumpPayload(boolean active) implements CustomPayload {
    public static final CustomPayload.Id<AirJumpPayload> ID = new CustomPayload.Id<>(Identifier.of("adventuremod", "air_jump"));
    public static final PacketCodec<PacketByteBuf, AirJumpPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, AirJumpPayload::active,
            AirJumpPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
