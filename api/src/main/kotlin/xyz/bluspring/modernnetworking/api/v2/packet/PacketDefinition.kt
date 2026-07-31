package xyz.bluspring.modernnetworking.api.v2.packet

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec

data class PacketDefinition<B : ByteBuf, T : NetworkPacket>(
    val namespace: String,
    val id: String,
    val codec: NetworkCodec<B, T>
)
