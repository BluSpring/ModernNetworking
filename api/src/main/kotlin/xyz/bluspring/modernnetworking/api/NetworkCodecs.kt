package xyz.bluspring.modernnetworking.api

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.internal.*
import java.util.UUID

object NetworkCodecs {
    @JvmField val BYTE = NetworkCodec(ByteBuf::writeByteActual, ByteBuf::readByte)
    @JvmField val FLOAT = NetworkCodec(ByteBuf::writeFloat, ByteBuf::readFloat)
    @JvmField val DOUBLE = NetworkCodec(ByteBuf::writeDouble, ByteBuf::readDouble)
    @JvmField val INT = NetworkCodec(ByteBuf::writeInt, ByteBuf::readInt)
    @JvmField val LONG = NetworkCodec(ByteBuf::writeLong, ByteBuf::readLong)
    @JvmField val BYTE_ARRAY = NetworkCodec(ByteBuf::writeByteArray, ByteBuf::readByteArray)

    @JvmField val VAR_INT = NetworkCodec(ByteBuf::writeVarInt, ByteBuf::readVarInt)
    @JvmField val VAR_LONG = NetworkCodec(ByteBuf::writeVarLong, ByteBuf::readVarLong)
    @JvmField val STRING_UTF8 = NetworkCodec(ByteBuf::writeUtf, ByteBuf::readUtf)
    @JvmField val UUID = NetworkCodec(ByteBuf::writeUUID, ByteBuf::readUUID)

    @JvmStatic
    fun stringUtf8(maxLength: Int) = NetworkCodec<String, ByteBuf>({ buf, value ->
        buf.writeUtf(value, maxLength)
    }, { buf ->
        buf.readUtf(maxLength)
    })

    @JvmStatic
    fun <E : Enum<E>> enumCodec(enumClass: Class<E>): NetworkCodec<E, ByteBuf> {
        val enumValues = enumClass.enumConstants

        return NetworkCodec({ buf, value ->
            buf.writeVarInt(value.ordinal)
        }, { buf ->
            enumValues[buf.readVarInt()]
        })
    }

    @JvmStatic
    fun <T> unit(value: T) = NetworkCodec<T, ByteBuf>({ buf, value -> }, { value })
}
