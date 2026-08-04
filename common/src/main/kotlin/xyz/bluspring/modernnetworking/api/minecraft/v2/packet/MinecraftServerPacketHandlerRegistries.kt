package xyz.bluspring.modernnetworking.api.minecraft.v2.packet

import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerLoginPacketListenerImpl
//? if >= 1.20.2 {
/*import net.minecraft.server.network.ServerConfigurationPacketListenerImpl
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerConfigurationPacketContext
*///? }
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerGamePacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerLoginPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.PlatformProxy

object MinecraftServerPacketHandlerRegistries {
    @JvmField val LOGIN: ServerLoginPacketHandlerRegistry<ServerLoginPacketContext, ServerLoginPacketListenerImpl> = PlatformProxy.instance.createServerLoginHandlerRegistry()
    //? if >= 1.20.2 {
    /*@JvmField val CONFIGURATION: ConfigurationPacketHandlerRegistry<ServerConfigurationPacketContext, ServerConfigurationPacketListenerImpl> = PlatformProxy.instance.createServerConfigHandlerRegistry()
    *///? }
    @JvmField val PLAY: PacketHandlerRegistry<ServerGamePacketContext, ServerPlayer> = PlatformProxy.instance.createServerGameHandlerRegistry()
}
