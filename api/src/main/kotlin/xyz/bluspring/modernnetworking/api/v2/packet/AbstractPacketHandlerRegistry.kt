package xyz.bluspring.modernnetworking.api.v2.packet

import io.netty.buffer.ByteBuf

abstract class AbstractPacketHandlerRegistry<C, R> : PacketHandlerRegistry<C, R> {
    val handlers = mutableMapOf<PacketDefinition<*, *>, PacketHandlerRegistry.PacketHandler<*, C>>()

    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, C>) {
        if (this.handlers.contains(definition))
            throw IllegalArgumentException("Packet handler for ${definition.namespace}:${definition.id} already exists!")

        this.handlers[definition] = handler
    }
}
