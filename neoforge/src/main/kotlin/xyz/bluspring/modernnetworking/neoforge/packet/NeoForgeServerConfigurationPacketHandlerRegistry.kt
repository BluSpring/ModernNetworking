package xyz.bluspring.modernnetworking.neoforge.packet

import net.minecraft.network.ConnectionProtocol
import net.minecraft.network.protocol.PacketFlow
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl
import net.neoforged.neoforge.network.handling.IPayloadContext
import net.neoforged.neoforge.server.ServerLifecycleHooks
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerConfigurationPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper

class NeoForgeServerConfigurationPacketHandlerRegistry : NeoForgePacketHandlerRegistry<ServerConfigurationPacketContext, ServerConfigurationPacketListenerImpl>(
    MinecraftPacketRegistries.SERVER_CONFIGURATION, PacketFlow.SERVERBOUND, ConnectionProtocol.CONFIGURATION
) {
    override fun createPayloadContext(context: IPayloadContext): ServerConfigurationPacketContext {
        // this is so hacky oml
        return ServerConfigurationPacketContext(context.listener() as ServerConfigurationPacketListenerImpl, ServerLifecycleHooks.getCurrentServer()!!)
    }

    override fun <T : NetworkPacket> send(receiver: ServerConfigurationPacketListenerImpl, packet: T) {
        receiver.send(CustomPayloadWrapper(this.packetRegistry, packet))
    }
}
