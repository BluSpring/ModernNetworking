package xyz.bluspring.modernnetworking.bukkit.api

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import org.bukkit.entity.Player
import xyz.bluspring.modernnetworking.api.NetworkCodec
import xyz.bluspring.modernnetworking.api.NetworkPacket

object BukkitNetworkSender {
    @JvmStatic
    fun <T : NetworkPacket> Player.sendPacketServer(packet: T) {
        val registry = BukkitNetworkRegistry.get(packet.definition.namespace) ?: throw IllegalArgumentException("Packet namespace ${packet.definition.namespace} does not exist! ($packet, ${packet.definition})")
        val buffer = Unpooled.buffer()
        (packet.definition.codec as NetworkCodec<T, ByteBuf>).encode(buffer, packet)

        this.sendPluginMessage(registry.plugin, "${packet.definition.namespace}:${packet.definition.id}", buffer.array())
    }
}