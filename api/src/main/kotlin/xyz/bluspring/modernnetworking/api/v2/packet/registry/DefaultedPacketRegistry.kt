package xyz.bluspring.modernnetworking.api.v2.packet.registry

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import java.util.Collections

/**
 * An implementation of [PacketRegistry] that registers packet definitions in a list.
 */
open class DefaultedPacketRegistry : PacketRegistry {
    protected val definitionsMutable: MutableList<PacketDefinition<*, *>> = Collections.synchronizedList(mutableListOf())
    val definitions: List<PacketDefinition<*, *>> = Collections.unmodifiableList(this.definitionsMutable)

    /**
     * Find a registered [PacketDefinition] based on the provided namespace and ID.
     */
    fun <B : ByteBuf, T : NetworkPacket> findDefinition(namespace: String, id: String): PacketDefinition<B, T>? {
        return this.definitionsMutable.firstOrNull {
            it.namespace == namespace && it.id == id
        } as? PacketDefinition<B, T>
    }

    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>): PacketDefinition<B, T> {
        this.definitionsMutable.add(definition)
        return definition
    }
}
