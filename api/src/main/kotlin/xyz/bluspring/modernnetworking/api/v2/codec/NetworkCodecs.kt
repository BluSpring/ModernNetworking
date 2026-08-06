package xyz.bluspring.modernnetworking.api.v2.codec

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.internal.*
import java.util.*

object NetworkCodecs {
    @JvmField val BOOL = NetworkCodec(ByteBuf::writeBoolean, ByteBuf::readBoolean)
    @JvmField val BYTE = NetworkCodec(ByteBuf::writeByteActual, ByteBuf::readByte)
    @JvmField val SHORT = NetworkCodec(ByteBuf::writeShortActual, ByteBuf::readShort)
    @JvmField val INT = NetworkCodec(ByteBuf::writeInt, ByteBuf::readInt)
    @JvmField val LONG = NetworkCodec(ByteBuf::writeLong, ByteBuf::readLong)
    @JvmField val FLOAT = NetworkCodec(ByteBuf::writeFloat, ByteBuf::readFloat)
    @JvmField val DOUBLE = NetworkCodec(ByteBuf::writeDouble, ByteBuf::readDouble)
    @JvmField val BYTE_ARRAY = NetworkCodec(ByteBuf::writeByteArray, ByteBuf::readByteArray)

    @JvmField val VAR_INT = NetworkCodec(ByteBuf::writeVarInt, ByteBuf::readVarInt)
    @JvmField val VAR_LONG = NetworkCodec(ByteBuf::writeVarLong, ByteBuf::readVarLong)
    @JvmField val STRING_UTF8 = NetworkCodec(ByteBuf::writeUtf, ByteBuf::readUtf)
    @JvmField val UUID = NetworkCodec(ByteBuf::writeUUID, ByteBuf::readUUID)

    @JvmStatic
    fun stringUtf8(maxLength: Int) = NetworkCodec<ByteBuf, String>({ buf, value ->
        buf.writeUtf(value, maxLength)
    }, { buf ->
        buf.readUtf(maxLength)
    })

    @JvmStatic
    fun <E : Enum<E>> enumCodec(enumClass: Class<E>): NetworkCodec<ByteBuf, E> {
        val enumValues = enumClass.enumConstants

        return NetworkCodec({ buf, value ->
            buf.writeVarInt(value.ordinal)
        }, { buf ->
            enumValues[buf.readVarInt()]
        })
    }

    @JvmStatic
    fun <E : Enum<E>> enumSetCodec(clazz: Class<E>): NetworkCodec<ByteBuf, EnumSet<E>> {
        return NetworkCodec({ buf, value ->
            buf.writeEnumSet(value, clazz)
        }, { buf ->
            buf.readEnumSet(clazz)
        })
    }

    @JvmStatic
    fun <T> unit(value: T) = NetworkCodec<ByteBuf, T>({ buf, value -> }, { value })

    @JvmStatic
    fun <T : Any, B : ByteBuf> NetworkCodec<B, T>.optional(): NetworkCodec<B, Optional<T>> {
        return this.nullable().xmap(Optional<T>::ofNullable) { it.orElse(null) }
    }

    @JvmStatic
    fun <T : Any, B : ByteBuf> NetworkCodec<B, T>.nullable(): NetworkCodec<B, T?> {
        return NetworkCodec({ buf, value ->
            buf.writeBoolean(value != null)

            if (value != null) {
                this.encode(buf, value)
            }
        }, { buf ->
            if (buf.readBoolean()) {
                return@NetworkCodec this.decode(buf)
            }

            return@NetworkCodec null
        })
    }

    @JvmStatic @JvmOverloads
    fun <T, B : ByteBuf, C : Collection<T>> NetworkCodec<B, T>.collectionOf(maxLength: Int = 32767, collectionProvider: () -> MutableCollection<T>): NetworkCodec<B, C> {
        return NetworkCodec({ buf, values ->
            if (maxLength > -1 && values.size > maxLength)
                throw IllegalArgumentException("Length of list to write to list codec $this is too long! (${values.size} > $maxLength)")

            buf.writeVarInt(values.size)
            for (value in values) {
                this.encode(buf, value)
            }
        }, { buf ->
            val list = collectionProvider()
            val length = buf.readVarInt()

            if (maxLength > -1 && length > maxLength)
                throw IllegalArgumentException("Length of list to read for list codec $this is too long! ($length > $maxLength)")

            for (i in 0 until length) {
                list.add(this.decode(buf))
            }

            return@NetworkCodec list as C
        })
    }

    @JvmStatic @JvmOverloads fun <T, B : ByteBuf> NetworkCodec<B, T>.listOf(maxLength: Int = 32767): NetworkCodec<B, List<T>> = this.collectionOf(maxLength, ::mutableListOf)
    @JvmStatic @JvmOverloads fun <T, B : ByteBuf> NetworkCodec<B, T>.setOf(maxLength: Int = 32767): NetworkCodec<B, Set<T>> = this.collectionOf(maxLength, ::mutableSetOf)
    @JvmStatic @JvmOverloads fun <T, B : ByteBuf> NetworkCodec<B, T>.sortedSetOf(maxLength: Int = 32767): NetworkCodec<B, SortedSet<T>> = this.collectionOf(maxLength, ::sortedSetOf)

    @JvmStatic @JvmOverloads
    fun <B : ByteBuf, K, V> map(keyCodec: NetworkCodec<B, K>, valueCodec: NetworkCodec<B, V>, maxLength: Int = 32767, mapProvider: () -> MutableMap<K, V> = ::mutableMapOf): NetworkCodec<B, Map<K, V>> {
        return NetworkCodec({ buf, value ->
            if (maxLength > -1 && value.size > maxLength)
                throw IllegalArgumentException("Length of map to write to map codec ($keyCodec: $valueCodec) is too long! (${value.size} > $maxLength)")

            buf.writeVarInt(value.size)

            for ((key, value) in value) {
                keyCodec.encode(buf, key)
                valueCodec.encode(buf, value)
            }
        }, { buf ->
            val map = mapProvider()
            val length = buf.readVarInt()

            if (maxLength > -1 && length > maxLength)
                throw IllegalArgumentException("Length of map to read for map codec ($keyCodec: $valueCodec) is too long! ($length > $maxLength)")

            for (i in 0 until length) {
                map[keyCodec.decode(buf)] = valueCodec.decode(buf)
            }

            map
        })
    }
}
