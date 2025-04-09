package xyz.bluspring.modernnetworking.api

import io.netty.buffer.ByteBuf

data class PacketDefinition<T : NetworkPacket, B : ByteBuf>(
    val namespace: String,
    val id: String,
    val codec: NetworkCodec<T, in B>
)
