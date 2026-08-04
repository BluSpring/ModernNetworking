package xyz.bluspring.modernnetworking.bukkit

import io.netty.buffer.ByteBuf
import org.bukkit.Bukkit
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.DefaultedPacketRegistry

class BukkitClientPacketRegistry : DefaultedPacketRegistry() {
    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>): PacketDefinition<B, T> {
        Bukkit.getMessenger().registerOutgoingPluginChannel(ModernNetworkingBukkit.plugin, "${definition.namespace}:${definition.id}")
        return super.register(definition)
    }
}
