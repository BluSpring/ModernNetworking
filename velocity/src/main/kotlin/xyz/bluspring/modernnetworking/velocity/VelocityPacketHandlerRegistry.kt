package xyz.bluspring.modernnetworking.velocity

import com.velocitypowered.api.proxy.messages.ChannelMessageSink
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.AbstractPacketHandlerRegistry
import xyz.bluspring.modernnetworking.velocity.api.v2.VelocityPacketContext

class VelocityPacketHandlerRegistry<R : ChannelMessageSink> : AbstractPacketHandlerRegistry<VelocityPacketContext, R>() {
    override fun <T : NetworkPacket> send(receiver: R, packet: T) {
        val buffer = Unpooled.buffer()
        packet.definition.codec.cast<ByteBuf, T>().encode(buffer, packet)

        receiver.sendPluginMessage(MinecraftChannelIdentifier.create(packet.definition.namespace, packet.definition.id), buffer.array())
    }
}
