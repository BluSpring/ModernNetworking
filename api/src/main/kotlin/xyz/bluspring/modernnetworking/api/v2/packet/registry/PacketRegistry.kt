package xyz.bluspring.modernnetworking.api.v2.packet.registry

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition

/**
 * A registry for [PacketDefinition]s to register into.
 */
interface PacketRegistry {
    fun <B : ByteBuf, T : NetworkPacket> register(namespace: String, id: String, codec: NetworkCodec<B, T>): PacketDefinition<B, T> {
        return register(PacketDefinition(namespace, id, codec))
    }

    /**
     * If you've created a [PacketDefinition] directly, you may use this to register to the packet registry.
     */
    fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>): PacketDefinition<B, T>

    /**
     * Creates a namespaced variant of this [PacketRegistry] to allow registering all [PacketDefinition]s under the same namespace
     * without specifying it.
     */
    fun namespaced(namespace: String): NamespacedPacketRegistry {
        return NamespacedPacketRegistry(this, namespace)
    }
}
