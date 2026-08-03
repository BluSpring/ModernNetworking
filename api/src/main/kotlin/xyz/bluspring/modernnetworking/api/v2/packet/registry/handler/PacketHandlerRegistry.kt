package xyz.bluspring.modernnetworking.api.v2.packet.registry.handler

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition

interface PacketHandlerRegistry<C, R> {
    fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandler<T, C>)

    fun <T : NetworkPacket> send(receiver: R, packet: T)

    fun interface PacketHandler<T : NetworkPacket, C> {
        fun handle(packet: T, context: C)
    }
}
