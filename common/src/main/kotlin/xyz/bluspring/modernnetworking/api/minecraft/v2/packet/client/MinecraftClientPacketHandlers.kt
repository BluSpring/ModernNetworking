package xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client

//? if >= 1.20.2 {
/*import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientConfigurationPacketContext
*///? }
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientGamePacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientLoginPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.SingleReceiverPacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.PlatformProxy

object MinecraftClientPacketHandlers {
    @JvmField val LOGIN: SingleReceiverPacketHandlerRegistry<ClientLoginPacketContext> = PlatformProxy.instance.createClientLoginHandlerRegistry()
    //? if >= 1.20.2 {
    /*@JvmField val CONFIGURATION: SingleReceiverPacketHandlerRegistry<ClientConfigurationPacketContext> = PlatformProxy.instance.createClientConfigHandlerRegistry()
    *///? }
    @JvmField val PLAY: SingleReceiverPacketHandlerRegistry<ClientGamePacketContext> = PlatformProxy.instance.createClientGameHandlerRegistry()
}
