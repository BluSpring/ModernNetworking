package xyz.bluspring.modernnetworking.api.v2.packet.registry

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import java.util.Collections

/**
 * A namespaced variant of a [PacketRegistry], allowing you to collect all of your registered packets in one place and register with only providing one namespace.
 */
class NamespacedPacketRegistry(private val backing: PacketRegistry, val namespace: String) {
    private val mutableDefinitions = mutableListOf<PacketDefinition<*, *>>()
    val definitions: List<PacketDefinition<*, *>> = Collections.unmodifiableList(mutableDefinitions)

    fun <B : ByteBuf, T : NetworkPacket> register(id: String, codec: NetworkCodec<B, T>): PacketDefinition<B, T> {
        val definition = this.backing.register(this.namespace, id, codec)
        this.mutableDefinitions.add(definition)

        return definition
    }

    /**
     * If you've created a [PacketDefinition] directly, you may use this to register to the packet registry.
     */
    fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>): PacketDefinition<B, T> {
        this.mutableDefinitions.add(definition)
        return this.backing.register(definition)
    }
}
