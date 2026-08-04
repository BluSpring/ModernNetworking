package xyz.bluspring.modernnetworking.api.v2.packet.registry

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec
import xyz.bluspring.modernnetworking.api.v2.packet.DualPacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition

/**
 * A registry for packet registries where both the serverbound and clientbound packets are required to share the same IDs, but are capable of holding different data.
 */
interface DualPacketRegistry {
    fun <B : ByteBuf, S : NetworkPacket, C : NetworkPacket> register(namespace: String, id: String, serverboundCodec: NetworkCodec<B, S>, clientboundCodec: NetworkCodec<B, C>): DualPacketDefinition<B, S, C> {
        return register(PacketDefinition(namespace, id, serverboundCodec), PacketDefinition(namespace, id, clientboundCodec))
    }

    /**
     * If you've created both [PacketDefinition]s directly, you may use this to register to the packet registry.
     */
    fun <B : ByteBuf, S : NetworkPacket, C : NetworkPacket> register(serverbound: PacketDefinition<B, S>, clientbound: PacketDefinition<B, C>): DualPacketDefinition<B, S, C>

    /**
     * Creates a namespaced variant of this [DualPacketRegistry] to allow registering all [PacketDefinition]s under the same namespace
     * without specifying it.
     */
    fun namespaced(namespace: String): NamespacedDualPacketRegistry {
        return NamespacedDualPacketRegistry(this, namespace)
    }
}
