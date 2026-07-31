package xyz.bluspring.modernnetworking.minecraft

import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl
import net.minecraft.server.network.ServerLoginPacketListenerImpl
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.ServerLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientConfigurationPacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientGamePacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientLoginPacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerConfigurationPacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerGamePacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerLoginPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.DefaultedPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.PacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.SingleReceiverPacketHandlerRegistry
import java.util.ServiceLoader

interface PlatformProxy {
    fun canLoad(): Boolean = true

    fun createServerLoginRegistry(): PacketRegistry
    fun createServerConfigRegistry(): MinecraftPacketRegistry
    fun createServerGameRegistry(): MinecraftPacketRegistry

    fun createClientLoginRegistry(): PacketRegistry
    fun createClientConfigRegistry(): MinecraftPacketRegistry
    fun createClientGameRegistry(): MinecraftPacketRegistry

    fun createServerLoginHandlerRegistry(): ServerLoginPacketHandlerRegistry<ServerLoginPacketContext, ServerLoginPacketListenerImpl>
    fun createServerConfigHandlerRegistry(): PacketHandlerRegistry<ServerConfigurationPacketContext, ServerConfigurationPacketListenerImpl>
    fun createServerGameHandlerRegistry(): PacketHandlerRegistry<ServerGamePacketContext, ServerPlayer>

    fun createClientLoginHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientLoginPacketContext>
    fun createClientConfigHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientConfigurationPacketContext>
    fun createClientGameHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientGamePacketContext>

    companion object {
        val instance: PlatformProxy = ServiceLoader.load(PlatformProxy::class.java)
            .first { it.canLoad() }
    }
}
