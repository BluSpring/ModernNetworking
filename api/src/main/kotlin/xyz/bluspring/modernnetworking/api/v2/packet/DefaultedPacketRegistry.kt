package xyz.bluspring.modernnetworking.api.v2.packet

import io.netty.buffer.ByteBuf

/**
 * An implementation of [PacketRegistry] that registers packet definitions in a list.
 */
open class DefaultedPacketRegistry : PacketRegistry {
    protected val definitions = mutableListOf<PacketDefinition<*, *>>()

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
