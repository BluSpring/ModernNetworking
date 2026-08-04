package xyz.bluspring.modernnetworking.forge

import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerLoginPacketListenerImpl
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.ServerLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.ClientLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientGamePacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientLoginPacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerGamePacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerLoginPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.registry.DefaultedDualPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.DualPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.SingleReceiverPacketHandlerRegistry
import xyz.bluspring.modernnetworking.forge.packet.ForgeServerGamePacketHandlerRegistry
import xyz.bluspring.modernnetworking.forge.packet.ForgeServerLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.forge.packet.client.ForgeClientGamePacketHandlerRegistry
import xyz.bluspring.modernnetworking.forge.packet.client.ForgeClientLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.PlatformProxy

class ForgePlatformProxy : PlatformProxy {
    override fun createLoginRegistry(): DualPacketRegistry = DefaultedDualPacketRegistry()
    override fun createServerGameRegistry(): MinecraftPacketRegistry = MinecraftPacketRegistry()
    override fun createClientGameRegistry(): MinecraftPacketRegistry = MinecraftPacketRegistry()

    override fun createServerLoginHandlerRegistry(): ServerLoginPacketHandlerRegistry<ServerLoginPacketContext, ServerLoginPacketListenerImpl> = ForgeServerLoginPacketHandlerRegistry()
    override fun createClientLoginHandlerRegistry(): ClientLoginPacketHandlerRegistry<ClientLoginPacketContext> = ForgeClientLoginPacketHandlerRegistry()
    override fun createServerGameHandlerRegistry(): PacketHandlerRegistry<ServerGamePacketContext, ServerPlayer> = ForgeServerGamePacketHandlerRegistry()
    override fun createClientGameHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientGamePacketContext> = ForgeClientGamePacketHandlerRegistry()
}
