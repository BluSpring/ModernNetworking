package xyz.bluspring.modernnetworking.fabric

//? if >= 1.20.5 {
/*import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import xyz.bluspring.modernnetworking.fabric.packet.FabricPacketRegistry
*///? }
//? if >= 1.20.2 {
/*import net.minecraft.server.network.ServerConfigurationPacketListenerImpl
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.ConfigurationPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientConfigurationPacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerConfigurationPacketContext
import xyz.bluspring.modernnetworking.fabric.packet.FabricServerConfigurationPacketHandlerRegistry
import xyz.bluspring.modernnetworking.fabric.packet.client.FabricClientConfigurationPacketHandlerRegistry
*///? }
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
import xyz.bluspring.modernnetworking.fabric.packet.FabricServerGamePacketHandlerRegistry
import xyz.bluspring.modernnetworking.fabric.packet.FabricServerLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.fabric.packet.client.FabricClientGamePacketHandlerRegistry
import xyz.bluspring.modernnetworking.fabric.packet.client.FabricClientLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.PlatformProxy

class FabricPlatformProxy : PlatformProxy {
    override fun createLoginRegistry(): PacketRegistry = DefaultedPacketRegistry()
    override fun createServerLoginHandlerRegistry(): ServerLoginPacketHandlerRegistry<ServerLoginPacketContext, ServerLoginPacketListenerImpl> = FabricServerLoginPacketHandlerRegistry()
    override fun createClientLoginHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientLoginPacketContext> = FabricClientLoginPacketHandlerRegistry()

    //? if >= 1.20.2 {
    /*override fun createServerConfigRegistry(): MinecraftPacketRegistry = FabricPacketRegistry(PayloadTypeRegistry.configurationC2S())
    override fun createClientConfigRegistry(): MinecraftPacketRegistry = FabricPacketRegistry(PayloadTypeRegistry.playS2C())
    override fun createServerConfigHandlerRegistry(): ConfigurationPacketHandlerRegistry<ServerConfigurationPacketContext, ServerConfigurationPacketListenerImpl> = FabricServerConfigurationPacketHandlerRegistry()
    override fun createClientConfigHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientConfigurationPacketContext> = FabricClientConfigurationPacketHandlerRegistry()
    *///? }

    //? if >= 1.20.5 {
    /*override fun createServerGameRegistry(): MinecraftPacketRegistry = FabricPacketRegistry(PayloadTypeRegistry.playC2S())
    override fun createClientGameRegistry(): MinecraftPacketRegistry = FabricPacketRegistry(PayloadTypeRegistry.configurationS2C())
    *///? } else {
    override fun createServerGameRegistry(): MinecraftPacketRegistry = MinecraftPacketRegistry()
    override fun createClientGameRegistry(): MinecraftPacketRegistry = MinecraftPacketRegistry()
    //? }
    override fun createServerGameHandlerRegistry(): PacketHandlerRegistry<ServerGamePacketContext, ServerPlayer> = FabricServerGamePacketHandlerRegistry()
    override fun createClientGameHandlerRegistry(): SingleReceiverPacketHandlerRegistry<ClientGamePacketContext> = FabricClientGamePacketHandlerRegistry()
}
