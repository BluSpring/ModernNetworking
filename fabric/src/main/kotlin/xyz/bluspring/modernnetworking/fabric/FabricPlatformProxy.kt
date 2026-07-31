package xyz.bluspring.modernnetworking.fabric

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
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
import xyz.bluspring.modernnetworking.fabric.packet.FabricPacketRegistry
import xyz.bluspring.modernnetworking.fabric.packet.FabricServerConfigurationPacketHandlerRegistry
import xyz.bluspring.modernnetworking.fabric.packet.FabricServerGamePacketHandlerRegistry
import xyz.bluspring.modernnetworking.fabric.packet.FabricServerLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.fabric.packet.client.FabricClientConfigurationPacketHandlerRegistry
import xyz.bluspring.modernnetworking.fabric.packet.client.FabricClientGamePacketHandlerRegistry
import xyz.bluspring.modernnetworking.fabric.packet.client.FabricClientLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.PlatformProxy

class FabricPlatformProxy : PlatformProxy {
    override fun createServerLoginRegistry(): PacketRegistry = DefaultedPacketRegistry()
    override fun createServerConfigRegistry(): MinecraftPacketRegistry = FabricPacketRegistry(PayloadTypeRegistry.configurationC2S())
    override fun createServerGameRegistry(): MinecraftPacketRegistry = FabricPacketRegistry(PayloadTypeRegistry.playC2S())

    override fun createClientLoginRegistry(): PacketRegistry = DefaultedPacketRegistry()
    override fun createClientConfigRegistry(): MinecraftPacketRegistry = FabricPacketRegistry(PayloadTypeRegistry.playS2C())
    override fun createClientGameRegistry(): MinecraftPacketRegistry = FabricPacketRegistry(PayloadTypeRegistry.configurationS2C())

    override fun createServerLoginHandlerRegistry(): ServerLoginPacketHandlerRegistry<ServerLoginPacketContext, ServerLoginPacketListenerImpl> = FabricServerLoginPacketHandlerRegistry()
    override fun createServerConfigHandlerRegistry(): PacketHandlerRegistry<ServerConfigurationPacketContext, ServerConfigurationPacketListenerImpl> = FabricServerConfigurationPacketHandlerRegistry()
    override fun createServerGameHandlerRegistry(): PacketHandlerRegistry<ServerGamePacketContext, ServerPlayer> = FabricServerGamePacketHandlerRegistry()

    override fun createClientLoginHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientLoginPacketContext> = FabricClientLoginPacketHandlerRegistry()
    override fun createClientConfigHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientConfigurationPacketContext> = FabricClientConfigurationPacketHandlerRegistry()
    override fun createClientGameHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientGamePacketContext> = FabricClientGamePacketHandlerRegistry()
}
