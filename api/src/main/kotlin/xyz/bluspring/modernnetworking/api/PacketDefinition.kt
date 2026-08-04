@file:Suppress("DEPRECATION_ERROR")
package xyz.bluspring.modernnetworking.api

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.internal.WrappedV1AsV2NetworkPacket

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Refactored to properly match MC 1.20.6.", replaceWith = ReplaceWith("PacketDefinition", "xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition"))
data class PacketDefinition<T : NetworkPacket, B : ByteBuf>(
    val namespace: String,
    val id: String,
    val codec: NetworkCodec<T, in B>
) {
    val asV2: PacketDefinition<B, WrappedV1AsV2NetworkPacket<T>>
        get() {
            return PacketDefinition(this.namespace, this.id, xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec({ buf, value ->
                this.codec.encode(buf, value.original)
            }, { buf ->
                val packet = this.codec.decode(buf)
                WrappedV1AsV2NetworkPacket(packet)
            }))
        }
}
