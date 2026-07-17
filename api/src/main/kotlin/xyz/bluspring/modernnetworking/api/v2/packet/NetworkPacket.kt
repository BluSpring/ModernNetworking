package xyz.bluspring.modernnetworking.api.v2.packet

import io.netty.buffer.ByteBuf

interface NetworkPacket {
    /**
     * The packet definition for this network packet.
     */
    val definition: PacketDefinition<out ByteBuf, out NetworkPacket>
}
