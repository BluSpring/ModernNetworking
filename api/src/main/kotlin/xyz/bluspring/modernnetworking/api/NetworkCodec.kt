package xyz.bluspring.modernnetworking.api

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec

@Deprecated(level = DeprecationLevel.ERROR, message = "Refactored to properly match MC 1.20.6.", replaceWith = ReplaceWith("NetworkCodec", "xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec"))
data class NetworkCodec<T, B : ByteBuf>(private val encoder: Encoder<B, T>, private val decoder: Decoder<B, T>) {
    fun encode(buffer: B, value: T) {
        this.encoder.encode(buffer, value)
    }

    fun decode(buffer: B): T {
        return this.decoder.decode(buffer)
    }

    fun asV2(): NetworkCodec<B, T> {
        return NetworkCodec(this.encoder::encode, this.decoder::decode)
    }

    fun interface Encoder<B : ByteBuf, T> {
        fun encode(buf: B, value: T)
    }

    fun interface Decoder<B : ByteBuf, T> {
        fun decode(buf: B): T
    }
}
