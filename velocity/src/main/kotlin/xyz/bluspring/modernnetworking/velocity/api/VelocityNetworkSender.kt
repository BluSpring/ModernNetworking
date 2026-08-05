@file:Suppress("DEPRECATION_ERROR")
package xyz.bluspring.modernnetworking.velocity.api

import com.velocitypowered.api.proxy.messages.ChannelMessageSink
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import xyz.bluspring.modernnetworking.api.NetworkCodec
import xyz.bluspring.modernnetworking.api.NetworkPacket

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Rewritten to utilize the new v2 API")
object VelocityNetworkSender {
    fun <T : NetworkPacket> ChannelMessageSink.sendPacket(packet: T) {
        val buffer = Unpooled.buffer()
        (packet.definition.codec as NetworkCodec<T, ByteBuf>).encode(buffer, packet)

        this.sendPluginMessage(MinecraftChannelIdentifier.create(packet.definition.namespace, packet.definition.id), buffer.array())
    }
}
