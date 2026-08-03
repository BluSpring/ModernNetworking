package xyz.bluspring.modernnetworking.api.v2.packet.registry

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition

interface PacketRegistry {
    fun <B : ByteBuf, T : NetworkPacket> register(namespace: String, id: String, codec: NetworkCodec<B, T>): PacketDefinition<B, T> {
        return register(PacketDefinition(namespace, id, codec))
    }

    fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>): PacketDefinition<B, T>
}
