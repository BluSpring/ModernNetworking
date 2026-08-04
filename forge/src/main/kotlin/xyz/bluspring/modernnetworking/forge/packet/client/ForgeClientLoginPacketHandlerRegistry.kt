package xyz.bluspring.modernnetworking.forge.packet.client

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerLoginPacketListenerImpl
import net.minecraftforge.network.NetworkEvent
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.event.EventNetworkChannel
import net.minecraftforge.server.ServerLifecycleHooks
import xyz.bluspring.modernnetworking.api.minecraft.v2.PacketDefinitionHelpers.identifier
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.ServerLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.ClientLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientLoginPacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerGamePacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerLoginPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry

class ForgeClientLoginPacketHandlerRegistry : ClientLoginPacketHandlerRegistry<ClientLoginPacketContext>() {
    override fun <B : ByteBuf, T : NetworkPacket> registerLogin(definition: PacketDefinition<B, T>, handler: LoginPacketHandler<T, ClientLoginPacketContext>) {
        val channel = NetworkRegistry.newEventChannel(definition.identifier, { "1" }, { true }, { true })
        channel.addListener<NetworkEvent.ClientCustomPayloadLoginEvent> { event ->
            val ctx = event.source.get()
            val client = Minecraft.getInstance()

            val packetCtx = ClientLoginPacketContext(ctx.networkManager.packetListener as ClientHandshakePacketListenerImpl, client)
            val buf = event.payload!!
            val packet = definition.codec.cast<FriendlyByteBuf, T>().decode(buf)
            handler.handle(packet, packetCtx)

            ctx.packetHandled = true
        }
    }
}
