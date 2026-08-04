package xyz.bluspring.modernnetworking.api.v2.packet.registry

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.packet.DualPacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import java.util.Collections

/**
 * An implementation of [DualPacketRegistry] that registers packet definitions in a list.
 */
open class DefaultedDualPacketRegistry : DualPacketRegistry {
    protected val definitionsMutable: MutableList<DualPacketDefinition<*, *, *>> = Collections.synchronizedList(mutableListOf())
    val definitions: List<DualPacketDefinition<*, *, *>> = Collections.unmodifiableList(this.definitionsMutable)

    /**
     * Find a registered [PacketDefinition] based on the provided namespace and ID.
     */
    fun <B : ByteBuf, S : NetworkPacket, C : NetworkPacket> findDefinition(namespace: String, id: String): DualPacketDefinition<B, S, C>? {
        return this.definitionsMutable.firstOrNull {
            it.serverbound.namespace == namespace && it.serverbound.id == id
        } as? DualPacketDefinition<B, S, C>
    }

    override fun <B : ByteBuf, S : NetworkPacket, C : NetworkPacket> register(serverbound: PacketDefinition<B, S>, clientbound: PacketDefinition<B, C>): DualPacketDefinition<B, S, C> {
        if (serverbound.namespace != clientbound.namespace || serverbound.id != clientbound.id)
            throw IllegalArgumentException("Tried registering two mismatched definitions in a dual packet registry! (serverbound: ${serverbound.namespace}:${serverbound.id}, clientbound: ${clientbound.namespace}:${clientbound.id})")

        val definition = DualPacketDefinition(serverbound, clientbound)
        this.definitionsMutable.add(definition)
        return definition
    }
}
