package xyz.bluspring.modernnetworking.api.v2.packet

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec

/**
 * A type that provides two [PacketDefinition]s, for [serverbound] packets and [clientbound] packets.
 * Both packet definitions are guaranteed to share the same namespace and ID.
 */
data class DualPacketDefinition<B : ByteBuf, S : NetworkPacket, C : NetworkPacket>(
    val serverbound: PacketDefinition<B, S>,
    val clientbound: PacketDefinition<B, C>,
)
