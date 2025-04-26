package xyz.bluspring.modernnetworking.bukkit.api

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.messaging.PluginMessageListener
import xyz.bluspring.modernnetworking.api.AbstractNetworkRegistry
import xyz.bluspring.modernnetworking.api.NetworkPacket
import xyz.bluspring.modernnetworking.api.PacketDefinition

class BukkitNetworkRegistry(private val plugin: Plugin, namespace: String? = null) : AbstractNetworkRegistry<Any, Server>(namespace ?: plugin.name.lowercase()), PluginMessageListener {
    override fun <T : NetworkPacket, B : ByteBuf> registerClientbound(definition: PacketDefinition<T, B>): PacketDefinition<T, B> {
        val definition = super.registerClientbound(definition)
        Bukkit.getMessenger().registerOutgoingPluginChannel(this.plugin, "${definition.namespace}:${definition.id}")
        return definition
    }

    override fun <T : NetworkPacket, B : ByteBuf> registerServerbound(definition: PacketDefinition<T, B>): PacketDefinition<T, B> {
        val definition = super.registerServerbound(definition);
        Bukkit.getMessenger().registerIncomingPluginChannel(this.plugin, "${definition.namespace}:${definition.id}", this)
        return definition
    }

    override fun onPluginMessageReceived(
        channel: String,
        player: Player,
        message: ByteArray?
    ) {
        val split = channel.split(":")
        val namespace = split[0]

        if (namespace != this.namespace)
            return

        val path = split.drop(1).joinToString(":") // not sure how this works in Vanilla but we'll handle it anyway

        val definition = this.getServerboundDefinition<ByteBuf>(path) ?: return
        val buffer = Unpooled.buffer()

        if (message != null)
            buffer.writeBytes(message)

        this.handleServerPacket(definition, buffer, Bukkit.getServer())
    }
}