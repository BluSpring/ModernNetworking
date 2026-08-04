package xyz.bluspring.modernnetworking.api.v2.packet

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec

/**
 * A type that represents the exact namespace, ID and [NetworkCodec] that composes a [NetworkPacket].
 */
data class PacketDefinition<B : ByteBuf, T : NetworkPacket>(
    /**
     * Represents the namespace that this [PacketDefinition] is registered under.
     * It is highly recommended for this to match your mod ID.
     */
    val namespace: String,
    val id: String,
    val codec: NetworkCodec<B, T>,
)
