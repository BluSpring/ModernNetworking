package xyz.bluspring.modernnetworking.forge.packet

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
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
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerGamePacketContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerLoginPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry

class ForgeServerLoginPacketHandlerRegistry : ServerLoginPacketHandlerRegistry<ServerLoginPacketContext, ServerLoginPacketListenerImpl>() {
    override fun <B : ByteBuf, T : NetworkPacket> registerLogin(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ServerLoginPacketContext>, unknownHandler: UnknownPacketHandler<ServerLoginPacketContext>) {
        val channel = NetworkRegistry.newEventChannel(definition.identifier, { "1" }, { true }, { true })
        channel.addListener<NetworkEvent.ServerCustomPayloadLoginEvent> { event ->
            val wasUnderstood = event.payload != null
            val ctx = event.source.get()
            val listener = ctx.networkManager.packetListener as ServerLoginPacketListenerImpl
            val packetCtx = ServerLoginPacketContext(listener, ServerLifecycleHooks.getCurrentServer()!!, { packet ->
                ctx.networkManager.send(packet)
            }, { packet ->
                val buf = FriendlyByteBuf(Unpooled.buffer())
                packet.definition.codec.cast<FriendlyByteBuf, NetworkPacket>().encode(buf, packet)
                ctx.networkManager.send(ClientboundCustomQueryPacket(event.loginIndex + 1, packet.definition.identifier, buf))
            }, wasUnderstood, { future ->
                TODO("Not yet implemented. Blame Forge.")
            })

            if (wasUnderstood) {
                val buf = event.payload!!
                val packet = definition.codec.cast<FriendlyByteBuf, T>().decode(buf)
                handler.handle(packet, packetCtx)
            } else {
                unknownHandler.handle(packetCtx)
            }

            ctx.packetHandled = true
        }
    }
}
