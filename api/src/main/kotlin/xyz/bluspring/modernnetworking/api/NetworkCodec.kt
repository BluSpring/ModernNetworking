package xyz.bluspring.modernnetworking.api

import io.netty.buffer.ByteBuf

data class NetworkCodec<T, B : ByteBuf>(private val encoder: (B, T) -> Unit, private val decoder: (B) -> T) {
    fun encode(buffer: B, value: T) {
        this.encoder.invoke(buffer, value)
    }

    fun decode(buffer: B): T {
        return this.decoder.invoke(buffer)
    }
}
