package xyz.bluspring.modernnetworking.fabric.packet

import io.netty.buffer.ByteBuf
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.level.ServerPlayer
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerGamePacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper
import xyz.bluspring.modernnetworking.minecraft.MinecraftPacketHandlerRegistry

class FabricServerGamePacketHandlerRegistry : MinecraftPacketHandlerRegistry<ServerGamePacketContext, ServerPlayer>(MinecraftPacketRegistries.SERVER_PLAY) {
    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ServerGamePacketContext>) {
        val type = this.packetRegistry.getOrCreateType(definition)
        PayloadTypeRegistry.playC2S().register(type.type, type.codec)
    }

    override fun <T : NetworkPacket> send(receiver: ServerPlayer, packet: T) {
        ServerPlayNetworking.send(receiver, CustomPayloadWrapper(this.packetRegistry, packet))
    }
}
