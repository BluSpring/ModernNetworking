package xyz.bluspring.modernnetworking.api.v2.packet

import io.netty.buffer.ByteBuf

interface PacketHandlerRegistry<C, R> {
    fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandler<T, C>)

    fun <T : NetworkPacket> send(receiver: R, packet: T)

    fun interface PacketHandler<T : NetworkPacket, C> {
        fun handle(packet: T, context: C)
    }
}
