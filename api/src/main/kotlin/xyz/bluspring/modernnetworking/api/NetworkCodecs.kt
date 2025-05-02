package xyz.bluspring.modernnetworking.api

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.internal.*
import java.util.EnumSet
import java.util.UUID

object NetworkCodecs {
    @JvmField val BOOL = NetworkCodec(ByteBuf::writeBoolean, ByteBuf::readBoolean)
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
    fun <E : Enum<E>> enumSetCodec(clazz: Class<E>): NetworkCodec<EnumSet<E>, ByteBuf> {
        return NetworkCodec({ buf, value ->
            buf.writeEnumSet(value, clazz)
        }, { buf ->
            buf.readEnumSet(clazz)
        })
    }

    @JvmStatic
    fun <T> unit(value: T) = NetworkCodec<T, ByteBuf>({ buf, value -> }, { value })

    @JvmStatic
    fun <T, B : ByteBuf> createNullable(original: NetworkCodec<T, B>): NetworkCodec<T?, B> {
        return NetworkCodec({ buf, value ->
            buf.writeBoolean(value != null)

            if (value != null) {
                original.encode(buf, value)
            }
        }, { buf ->
            if (buf.readBoolean()) {
                return@NetworkCodec original.decode(buf)
            }

            return@NetworkCodec null
        })
    }

    @JvmStatic
    fun <T, B : ByteBuf> toList(original: NetworkCodec<T, B>): NetworkCodec<List<T>, B> {
        return NetworkCodec({ buf, values ->
            buf.writeVarInt(values.size)
            for (value in values) {
                original.encode(buf, value)
            }
        }, { buf ->
            val list = mutableListOf<T>()
            val length = buf.readVarInt()

            for (i in 0 until length) {
                list.add(original.decode(buf))
            }

            return@NetworkCodec list.toList()
        })
    }

    @JvmStatic
    fun <B : ByteBuf, K, V> createMap(keyCodec: NetworkCodec<K, B>, valueCodec: NetworkCodec<V, B>): NetworkCodec<Map<K, V>, B> {
        return NetworkCodec({ buf, value ->
            buf.writeVarInt(value.size)

            for ((key, value) in value) {
                keyCodec.encode(buf, key)
                valueCodec.encode(buf, value)
            }
        }, { buf ->
            val map = mutableMapOf<K, V>()
            val length = buf.readVarInt()

            for (i in 0 until length) {
                map[keyCodec.decode(buf)] = valueCodec.decode(buf)
            }

            map
        })
    }
}
