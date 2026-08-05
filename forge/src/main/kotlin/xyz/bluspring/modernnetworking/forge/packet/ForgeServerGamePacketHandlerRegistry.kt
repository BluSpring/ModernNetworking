package xyz.bluspring.modernnetworking.forge.packet

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.network.NetworkEvent
import net.minecraftforge.network.event.EventNetworkChannel
import xyz.bluspring.modernnetworking.minecraft.api.v2.PacketDefinitionHelpers.identifier
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.context.ServerGamePacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry

class ForgeServerGamePacketHandlerRegistry : ForgePacketHandlerRegistry<ServerGamePacketContext, ServerPlayer>() {
    override fun <B : ByteBuf, T : NetworkPacket> setupEventChannel(channel: EventNetworkChannel, definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ServerGamePacketContext>) {
        channel.addListener<NetworkEvent.ServerCustomPayloadEvent> { event ->
            val packet = definition.codec.cast<FriendlyByteBuf, T>().decode(event.payload)
            val ctx = event.source.get()
            ctx.enqueueWork {
                handler.handle(packet, ServerGamePacketContext(ctx.sender!!, ctx.sender!!.server))
            }

            ctx.packetHandled = true
        }
    }

    override fun <T : NetworkPacket> send(receiver: ServerPlayer, packet: T) {
        val buf = FriendlyByteBuf(Unpooled.buffer())
        packet.definition.codec.cast<FriendlyByteBuf, T>().encode(buf, packet)

        receiver.connection.send(ClientboundCustomPayloadPacket(packet.definition.identifier, buf))
    }
}
