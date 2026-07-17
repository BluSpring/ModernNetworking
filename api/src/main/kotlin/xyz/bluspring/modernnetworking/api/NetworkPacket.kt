package xyz.bluspring.modernnetworking.api

import io.netty.buffer.ByteBuf

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Refactored to properly match MC 1.20.6.", replaceWith = ReplaceWith("NetworkPacket", "xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket"))
interface NetworkPacket {
    /**
     * The packet definition for this network packet.
     */
    val definition: PacketDefinition<out NetworkPacket, out ByteBuf>
}
