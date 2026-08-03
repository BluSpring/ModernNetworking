package xyz.bluspring.modernnetworking.fabric.packet

//? if >= 1.20.2 {
import io.netty.buffer.ByteBuf
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerConfigurationPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper
import xyz.bluspring.modernnetworking.minecraft.MinecraftPacketHandlerRegistry

class FabricServerConfigurationPacketHandlerRegistry : MinecraftPacketHandlerRegistry<ServerConfigurationPacketContext, ServerConfigurationPacketListenerImpl>(MinecraftPacketRegistries.SERVER_CONFIGURATION) {
    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ServerConfigurationPacketContext>) {
        ServerConfigurationNetworking.registerGlobalReceiver(this.packetRegistry.getOrCreateType(definition).type) { packet, ctx ->
            handler.handle(packet.packet, ServerConfigurationPacketContext(ctx.networkHandler(), ctx.server()))
        }
    }

    override fun <T : NetworkPacket> send(receiver: ServerConfigurationPacketListenerImpl, packet: T) {
        ServerConfigurationNetworking.send(receiver, CustomPayloadWrapper(this.packetRegistry, packet))
    }
}
//? }
