package xyz.bluspring.modernnetworking.api.v2.codec

import io.netty.buffer.ByteBuf

@JvmRecord
data class NetworkCodec<B : ByteBuf, T>(private val encoder: Encoder<B, T>, private val decoder: Decoder<B, T>) {
    fun encode(buffer: B, value: T) {
        this.encoder.encode(buffer, value)
    }

    fun decode(buffer: B): T {
        return this.decoder.decode(buffer)
    }

    fun interface Encoder<B : ByteBuf, T> {
        fun encode(buf: B, value: T)
    }

    fun interface Decoder<B : ByteBuf, T> {
        fun decode(buf: B): T
    }
}
