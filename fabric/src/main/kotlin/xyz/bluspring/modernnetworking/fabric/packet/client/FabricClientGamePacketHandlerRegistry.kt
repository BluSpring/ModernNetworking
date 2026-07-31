package xyz.bluspring.modernnetworking.fabric.packet.client

import io.netty.buffer.ByteBuf
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientGamePacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper
import xyz.bluspring.modernnetworking.minecraft.MinecraftSingleReceiverPacketHandlerRegistry

class FabricClientGamePacketHandlerRegistry : MinecraftSingleReceiverPacketHandlerRegistry<ClientGamePacketContext>(MinecraftPacketRegistries.CLIENT_PLAY) {
    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ClientGamePacketContext>) {
        ClientPlayNetworking.registerGlobalReceiver(this.packetRegistry.getOrCreateType(definition).type) { packet, ctx ->
            handler.handle(packet.packet, ClientGamePacketContext(ctx.player(), ctx.client()))
        }
    }

    override fun <T : NetworkPacket> send(packet: T) {
        ClientPlayNetworking.send(CustomPayloadWrapper(this.packetRegistry, packet))
    }
}
