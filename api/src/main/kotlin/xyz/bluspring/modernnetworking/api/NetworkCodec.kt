package xyz.bluspring.modernnetworking.api

import io.netty.buffer.ByteBuf

data class NetworkCodec<T, B : ByteBuf>(private val encoder: Encoder<B, T>, private val decoder: Decoder<B, T>) {
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
