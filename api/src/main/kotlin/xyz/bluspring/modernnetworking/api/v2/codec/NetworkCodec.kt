package xyz.bluspring.modernnetworking.api.v2.codec

import io.netty.buffer.ByteBuf

data class NetworkCodec<B : ByteBuf, T>(private val encoder: Encoder<B, T>, private val decoder: Decoder<B, T>) {
    fun encode(buffer: B, value: T) {
        this.encoder.encode(buffer, value)
    }

    fun decode(buffer: B): T {
        return this.decoder.decode(buffer)
    }

    fun <C : ByteBuf, U> cast(): NetworkCodec<C, U> {
        return this as NetworkCodec<C, U>
    }

    fun <U> xmap(to: (T) -> U, from: (U) -> T): NetworkCodec<B, U> {
        return NetworkCodec({ buf, value ->
            this.encode(buf, from(value))
        }, { buf ->
            to(this.decode(buf))
        })
    }

    fun <U> dispatch(type: (U) -> T, codec: (T) -> NetworkCodec<B, U>): NetworkCodec<B, U> {
        return NetworkCodec({ buf, value ->
            val key = type(value)
            val codec = codec(key)
            this.encode(buf, key)
            codec.encode(buf, value)
        }, { buf ->
            val key = this.decode(buf)
            val codec = codec(key)
            codec.decode(buf)
        })
    }

    fun interface Encoder<B : ByteBuf, T> {
        fun encode(buf: B, value: T)
    }

    fun interface Decoder<B : ByteBuf, T> {
        fun decode(buf: B): T
    }
}
