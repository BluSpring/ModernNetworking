package xyz.bluspring.modernnetworking.api

import io.netty.buffer.ByteBuf

interface NetworkPacket {
    /**
     * The packet definition for this network packet.
     */
    val definition: PacketDefinition<out NetworkPacket, in ByteBuf>
}