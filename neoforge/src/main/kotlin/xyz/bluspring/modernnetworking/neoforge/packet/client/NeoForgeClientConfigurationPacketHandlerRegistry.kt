package xyz.bluspring.modernnetworking.neoforge.packet.client

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientConfigurationPacketListenerImpl
import net.minecraft.network.ConnectionProtocol
import net.minecraft.network.protocol.PacketFlow
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl
import net.neoforged.neoforge.network.handling.IPayloadContext
import net.neoforged.neoforge.server.ServerLifecycleHooks
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientConfigurationPacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerConfigurationPacketContext
import xyz.bluspring.modernnetworking.neoforge.packet.NeoForgePacketHandlerRegistry

class NeoForgeClientConfigurationPacketHandlerRegistry : NeoForgeClientPacketHandlerRegistry<ClientConfigurationPacketContext>(
    MinecraftPacketRegistries.CLIENT_CONFIGURATION, PacketFlow.CLIENTBOUND, ConnectionProtocol.CONFIGURATION
) {
    override fun createPayloadContext(context: IPayloadContext): ClientConfigurationPacketContext {
        // this is so hacky oml
        return ClientConfigurationPacketContext(context.listener() as ClientConfigurationPacketListenerImpl, Minecraft.getInstance())
    }
}
