package xyz.bluspring.modernnetworking.bukkit

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufUtil
import io.netty.buffer.Unpooled
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.bukkit.api.v2.BukkitPacketContext

class BukkitPacketHandlerRegistry :
    PacketHandlerRegistry<BukkitPacketContext, Player> {
    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, BukkitPacketContext>) {
        val id = "${definition.namespace}:${definition.id}"
        Bukkit.getMessenger().registerIncomingPluginChannel(ModernNetworkingBukkit.plugin, id) { _, player, message ->
            val buffer = Unpooled.buffer()
            if (message != null)
                buffer.writeBytes(message)

            val packet = (definition.codec as NetworkCodec<ByteBuf, T>).decode(buffer)
            handler.handle(packet, BukkitPacketContext(player))
        }
    }

    override fun <T : NetworkPacket> send(receiver: Player, packet: T) {
        val buffer = Unpooled.buffer()
        (packet.definition.codec as NetworkCodec<ByteBuf, T>).encode(buffer, packet)
        receiver.sendPluginMessage(ModernNetworkingBukkit.plugin, "${packet.definition.namespace}:${packet.definition.id}", ByteBufUtil.getBytes(buffer))
    }
}
