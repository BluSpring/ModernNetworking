package xyz.bluspring.modernnetworking.api.v2.packet.registry.handler

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition

abstract class AbstractPacketHandlerRegistry<C, R> : PacketHandlerRegistry<C, R> {
    val handlers = mutableMapOf<PacketDefinition<*, *>, PacketHandlerRegistry.PacketHandler<*, C>>()

    fun <T : NetworkPacket> handle(packet: T, context: C) {
        (this.handlers[packet.definition] as? PacketHandlerRegistry.PacketHandler<T, C>)
            ?.handle(packet, context)
    }

    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, C>) {
        if (this.handlers.contains(definition))
            throw IllegalArgumentException("Packet handler for ${definition.namespace}:${definition.id} already exists!")

        this.handlers[definition] = handler
    }
}
