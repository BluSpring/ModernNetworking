package xyz.bluspring.modernnetworking.api.v2.packet.registry

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition

/**
 * An implementation of [PacketRegistry] that registers packet definitions in a list.
 */
open class DefaultedPacketRegistry : PacketRegistry {
    protected val definitions = mutableListOf<PacketDefinition<*, *>>()

    /**
     * Find a registered [PacketDefinition] based on the provided namespace and ID.
     */
    fun <B : ByteBuf, T : NetworkPacket> findDefinition(namespace: String, id: String): PacketDefinition<B, T>? {
        return this.definitions.firstOrNull {
            it.namespace == namespace && it.id == id
        } as? PacketDefinition<B, T>
    }

    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>): PacketDefinition<B, T> {
        this.definitions.add(definition)
        return definition
    }
}
