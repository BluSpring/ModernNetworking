package xyz.bluspring.modernnetworking.api.v2.packet

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec

interface PacketRegistry {
    fun <B : ByteBuf, T : NetworkPacket> register(namespace: String, id: String, codec: NetworkCodec<B, T>): PacketDefinition<B, T> {
        return register(PacketDefinition(namespace, id, codec))
    }

    fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>): PacketDefinition<B, T>
}
