package xyz.bluspring.modernnetworking.minecraft

//? if >= 1.20.2 {
/*import net.minecraft.server.network.ServerConfigurationPacketListenerImpl
import xyz.bluspring.modernnetworking.minecraft.api.packet.v2.ConfigurationPacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.client.context.ClientConfigurationPacketContext
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.context.ServerConfigurationPacketContext
*///? }

import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerLoginPacketListenerImpl
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.MinecraftPacketRegistry
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.ServerLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.client.ClientLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.client.context.ClientGamePacketContext
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.client.context.ClientLoginPacketContext
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.context.ServerGamePacketContext
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.context.ServerLoginPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.registry.DualPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.SingleReceiverPacketHandlerRegistry
import java.util.*

interface PlatformProxy {
    fun canLoad(): Boolean = true

    fun createLoginRegistry(): DualPacketRegistry
    fun createServerLoginHandlerRegistry(): ServerLoginPacketHandlerRegistry<ServerLoginPacketContext, ServerLoginPacketListenerImpl>
    fun createClientLoginHandlerRegistry(): ClientLoginPacketHandlerRegistry<ClientLoginPacketContext>

    //? if >= 1.20.2 {
    /*fun createServerConfigRegistry(): MinecraftPacketRegistry
    fun createServerConfigHandlerRegistry(): ConfigurationPacketHandlerRegistry<ServerConfigurationPacketContext, ServerConfigurationPacketListenerImpl>
    fun createClientConfigRegistry(): MinecraftPacketRegistry
    fun createClientConfigHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientConfigurationPacketContext>
    *///? }

    fun createServerGameRegistry(): MinecraftPacketRegistry
    fun createServerGameHandlerRegistry(): PacketHandlerRegistry<ServerGamePacketContext, ServerPlayer>
    fun createClientGameRegistry(): MinecraftPacketRegistry
    fun createClientGameHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientGamePacketContext>

    companion object {
        val instance: PlatformProxy = ServiceLoader.load(PlatformProxy::class.java)
            .first { it.canLoad() }
    }
}
