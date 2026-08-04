package xyz.bluspring.modernnetworking.api.v2.packet.registry

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec
import xyz.bluspring.modernnetworking.api.v2.packet.DualPacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import java.util.Collections

/**
 * A namespaced variant of a [DualPacketRegistry], allowing you to collect all of your registered packets in one place and register with only providing one namespace.
 */
class NamespacedDualPacketRegistry(private val backing: DualPacketRegistry, val namespace: String) {
    private val mutableDefinitions = mutableListOf<DualPacketDefinition<*, *, *>>()
    val definitions: List<DualPacketDefinition<*, *, *>> = Collections.unmodifiableList(mutableDefinitions)

    fun <B : ByteBuf, S : NetworkPacket, C : NetworkPacket> register(id: String, serverboundCodec: NetworkCodec<B, S>, clientboundCodec: NetworkCodec<B, C>): DualPacketDefinition<B, S, C> {
        val definition = this.backing.register(this.namespace, id, serverboundCodec, clientboundCodec)
        this.mutableDefinitions.add(definition)

        return definition
    }

    /**
     * If you've created a [PacketDefinition] directly, you may use this to register to the packet registry.
     */
    fun <B : ByteBuf, S : NetworkPacket, C : NetworkPacket> register(serverbound: PacketDefinition<B, S>, clientbound: PacketDefinition<B, C>): DualPacketDefinition<B, S, C> {
        val definition = this.backing.register(serverbound, clientbound)
        this.mutableDefinitions.add(definition)
        return definition
    }
}
