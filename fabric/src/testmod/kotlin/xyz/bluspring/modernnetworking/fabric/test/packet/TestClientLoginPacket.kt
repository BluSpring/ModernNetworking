package xyz.bluspring.modernnetworking.fabric.test.packet

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.codec.CompositeCodecs
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodecs
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.fabric.test.ModernNetworkingFabricTest

data class TestClientLoginPacket(
    val testString: String,
    val testByteArray: ByteArray,
) : NetworkPacket {
    override val definition: PacketDefinition<out ByteBuf, out NetworkPacket>
        get() = ModernNetworkingFabricTest.TEST_CLIENT_LOGIN

    companion object {
        val CODEC = CompositeCodecs.composite(
            NetworkCodecs.STRING_UTF8, TestClientLoginPacket::testString,
            NetworkCodecs.BYTE_ARRAY, TestClientLoginPacket::testByteArray,
            ::TestClientLoginPacket
        )
    }
}
