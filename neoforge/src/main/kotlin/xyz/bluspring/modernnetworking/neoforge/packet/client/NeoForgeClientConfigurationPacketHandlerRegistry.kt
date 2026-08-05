package xyz.bluspring.modernnetworking.neoforge.packet.client

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientConfigurationPacketListenerImpl
import net.minecraft.network.ConnectionProtocol
import net.minecraft.network.protocol.PacketFlow
import net.neoforged.neoforge.network.handling.IPayloadContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientConfigurationPacketContext

class NeoForgeClientConfigurationPacketHandlerRegistry : NeoForgeClientPacketHandlerRegistry<ClientConfigurationPacketContext>(
    MinecraftPacketRegistries.CLIENT_CONFIGURATION, MinecraftPacketRegistries.SERVER_CONFIGURATION, PacketFlow.CLIENTBOUND, ConnectionProtocol.CONFIGURATION
) {
    override fun createPayloadContext(context: IPayloadContext): ClientConfigurationPacketContext {
        // this is so hacky oml
        //? if >= 1.20.5 {
        val listener = context.listener() as ClientConfigurationPacketListenerImpl
        //? } else {
        /*// okay nevermind the new one is better, what the fuck is this
        val listener = context.packetHandler().javaClass.getDeclaredMethod("listener").apply {
            this.isAccessible = true
        }.invoke(context.packetHandler()) as ClientConfigurationPacketListenerImpl
        *///? }
        return ClientConfigurationPacketContext(listener, Minecraft.getInstance())
    }
}
