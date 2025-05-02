package xyz.bluspring.modernnetworking.internal

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufUtil
import java.util.BitSet
import java.util.EnumSet
import java.util.UUID
import kotlin.experimental.and
import kotlin.text.toByte

internal const val SEGMENT_BITS = 0x7F
internal const val CONTINUE_BIT = 0x80

fun ByteBuf.readVarInt(): Int {
    var value = 0
    var position = 0

    while (true) {
        val currentByte = readByte()
        value = value or ((currentByte and SEGMENT_BITS.toByte()).toInt() shl position)

        if ((currentByte and CONTINUE_BIT.toByte()).toInt() == 0)
            break

        position += 7

        if (position >= 32)
            throw RuntimeException("VarInt too big!")
    }

    return value
}

fun ByteBuf.writeVarInt(value: Int) {
    var value = value

    while (true) {
        if ((value and SEGMENT_BITS.inv()) == 0) {
            writeByte(value)
            return
        }

        writeByte((value and SEGMENT_BITS) or CONTINUE_BIT)
        value = value ushr 7
    }
}

fun ByteBuf.readVarLong(): Long {
    var value = 0L
    var position = 0

    while (true) {
        val currentByte = readByte()
        value = value or ((currentByte and SEGMENT_BITS.toByte()).toLong() shl position)

        if ((currentByte and CONTINUE_BIT.toByte()).toLong() == 0L)
            break

        position += 7

        if (position >= 64)
            throw RuntimeException("VarLong too big!")
    }

    return value
}

fun ByteBuf.writeVarLong(value: Long) {
    var value = value

    while (true) {
        if ((value and SEGMENT_BITS.inv().toLong()) == 0L) {
            writeByte(value.toInt())
            return
        }

        writeByte(((value and SEGMENT_BITS.toLong()) or CONTINUE_BIT.toLong()).toInt())
        value = value ushr 7
    }
}

fun ByteBuf.readUtf(maxLength: Int = 32767): String {
    val maxBytes = ByteBufUtil.utf8MaxBytes(maxLength)
    val totalLength = this.readVarInt()

    if (totalLength > maxBytes)
        throw RuntimeException("The received encoded string buffer length is longer than maximum allowed ($totalLength > $maxBytes)")
    else if (totalLength < 0)
        throw RuntimeException("The received encoded string buffer length is less than zero! Weird string!")

    val readable = this.readableBytes()
    if (totalLength > readable)
        throw RuntimeException("Not enough bytes in buffer, expected $totalLength but got $readable!")

    val string = this.toString(this.readerIndex(), totalLength, Charsets.UTF_8)
    this.readerIndex(this.readerIndex() + totalLength)

    if (string.length > maxLength)
        throw RuntimeException("The received string length is longer than maximum allowed (${string.length} > $maxLength)")

    return string
}

fun ByteBuf.writeUtf(text: String, maxLength: Int = 32767) {
    if (text.length > maxLength)
        throw RuntimeException("String too big (was ${text.length} characters, max $maxLength)")

    val maxBytes = ByteBufUtil.utf8MaxBytes(text)
    val byteBuf = this.alloc().buffer(maxBytes)

    try {
        val written = ByteBufUtil.writeUtf8(byteBuf, text)
        val max = ByteBufUtil.utf8MaxBytes(maxLength)

        if (written > max)
            throw RuntimeException("String too big (was $written bytes encoded, max $max)")

        this.writeVarInt(written)
        this.writeBytes(byteBuf)
    } finally {
        byteBuf.release()
    }
}

fun ByteBuf.writeByteActual(byte: Byte) {
    this.writeByte(byte.toInt())
}

fun ByteBuf.readUUID(): UUID {
    return UUID(this.readLong(), this.readLong())
}

fun ByteBuf.writeUUID(uuid: UUID) {
    this.writeLong(uuid.mostSignificantBits)
    this.writeLong(uuid.leastSignificantBits)
}

fun ByteBuf.readByteArray(maxLength: Int = this.readableBytes()): ByteArray {
    val length = this.readVarInt()

    if (length > maxLength) {
        throw RuntimeException("ByteArray with size $length is bigger than allowed $maxLength!")
    }

    val byteArray = ByteArray(length)
    this.readBytes(byteArray)

    return byteArray
}

fun ByteBuf.writeByteArray(byteArray: ByteArray) {
    this.writeVarInt(byteArray.size)
    this.writeBytes(byteArray)
}

fun ByteBuf.readFixedBitSet(size: Int): BitSet {
    val bytes = ByteArray(-Math.floorDiv(-size, 8))
    this.readBytes(bytes)
    return BitSet.valueOf(bytes)
}

fun ByteBuf.writeFixedBitSet(bitSet: BitSet, size: Int) {
    if (bitSet.length() > size) {
        throw IndexOutOfBoundsException("BitSet is larger than expected size (${bitSet.length()} > $size)")
    }

    val bytes = bitSet.toByteArray()
    this.writeBytes(bytes.copyOf(-Math.floorDiv(-size, 8)))
}

fun <E : Enum<E>> ByteBuf.writeEnumSet(enumSet: EnumSet<E>, enumClass: Class<E>) {
    val enums = enumClass.enumConstants
    val bitSet = BitSet(enums.size)

    for ((i, e) in enums.withIndex()) {
        bitSet.set(i, enumSet.contains(e))
    }

    this.writeFixedBitSet(bitSet, enums.size)
}

fun <E : Enum<E>> ByteBuf.readEnumSet(enumClass: Class<E>): EnumSet<E> {
    val enums = enumClass.enumConstants
    val bitSet = this.readFixedBitSet(enums.size)
    val enumSet = EnumSet.noneOf(enumClass)

    for ((i, e) in enums.withIndex()) {
        if (bitSet.get(i)) {
            enumSet.add(e)
        }
    }

    return enumSet
}