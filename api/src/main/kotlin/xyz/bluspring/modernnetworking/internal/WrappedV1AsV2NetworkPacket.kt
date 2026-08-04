@file:Suppress("DEPRECATION_ERROR")
package xyz.bluspring.modernnetworking.internal

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.NetworkPacket as V1NetworkPacket

data class WrappedV1AsV2NetworkPacket<T : V1NetworkPacket>(val original: T) : NetworkPacket {
    override val definition: PacketDefinition<out ByteBuf, out NetworkPacket>
        get() = this.original.definition.asV2
}
