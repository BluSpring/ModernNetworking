package xyz.bluspring.modernnetworking.api

import io.netty.buffer.ByteBuf

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Refactored to properly match MC 1.20.6.", replaceWith = ReplaceWith("PacketDefinition", "xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition"))
data class PacketDefinition<T : NetworkPacket, B : ByteBuf>(
    val namespace: String,
    val id: String,
    val codec: NetworkCodec<T, in B>
)
