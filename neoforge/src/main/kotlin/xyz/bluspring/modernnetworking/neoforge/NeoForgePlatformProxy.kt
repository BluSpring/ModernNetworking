package xyz.bluspring.modernnetworking.neoforge

//? if >= 1.20.2 {
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientConfigurationPacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerConfigurationPacketContext
import xyz.bluspring.modernnetworking.neoforge.packet.NeoForgeServerConfigurationPacketHandlerRegistry
import xyz.bluspring.modernnetworking.neoforge.packet.client.NeoForgeClientConfigurationPacketHandlerRegistry
//? }
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerLoginPacketListenerImpl
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.ServerLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientGamePacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientLoginPacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerGamePacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerLoginPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.registry.DefaultedPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.PacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.SingleReceiverPacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.PlatformProxy
import xyz.bluspring.modernnetworking.neoforge.packet.NeoForgePacketRegistry
import xyz.bluspring.modernnetworking.neoforge.packet.NeoForgeServerGamePacketHandlerRegistry
import xyz.bluspring.modernnetworking.neoforge.packet.NeoForgeServerLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.neoforge.packet.client.NeoForgeClientGamePacketHandlerRegistry
import xyz.bluspring.modernnetworking.neoforge.packet.client.NeoForgeClientLoginPacketHandlerRegistry

class NeoForgePlatformProxy : PlatformProxy {
    override fun createServerLoginRegistry(): PacketRegistry = DefaultedPacketRegistry()
    override fun createClientLoginRegistry(): PacketRegistry = DefaultedPacketRegistry()
    override fun createServerLoginHandlerRegistry(): ServerLoginPacketHandlerRegistry<ServerLoginPacketContext, ServerLoginPacketListenerImpl> = NeoForgeServerLoginPacketHandlerRegistry()
    override fun createClientLoginHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientLoginPacketContext> = NeoForgeClientLoginPacketHandlerRegistry()

    //? if >= 1.20.2 {
    override fun createServerConfigRegistry(): MinecraftPacketRegistry = NeoForgePacketRegistry()
    override fun createClientConfigRegistry(): MinecraftPacketRegistry = NeoForgePacketRegistry()
    override fun createServerConfigHandlerRegistry(): PacketHandlerRegistry<ServerConfigurationPacketContext, ServerConfigurationPacketListenerImpl> = NeoForgeServerConfigurationPacketHandlerRegistry()
    override fun createClientConfigHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientConfigurationPacketContext> = NeoForgeClientConfigurationPacketHandlerRegistry()
    //? }

    override fun createServerGameRegistry(): MinecraftPacketRegistry = NeoForgePacketRegistry()
    override fun createClientGameRegistry(): MinecraftPacketRegistry = NeoForgePacketRegistry()
    override fun createServerGameHandlerRegistry(): PacketHandlerRegistry<ServerGamePacketContext, ServerPlayer> = NeoForgeServerGamePacketHandlerRegistry()
    override fun createClientGameHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientGamePacketContext> = NeoForgeClientGamePacketHandlerRegistry()
}
