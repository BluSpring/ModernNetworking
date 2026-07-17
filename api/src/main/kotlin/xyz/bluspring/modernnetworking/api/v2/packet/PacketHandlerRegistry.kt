package xyz.bluspring.modernnetworking.api.v2.packet

import io.netty.buffer.ByteBuf

interface PacketHandlerRegistry<C> {
    fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandler<T, C>)

    fun interface PacketHandler<T : NetworkPacket, C> {
        fun handle(packet: T, context: C)
    }
}
