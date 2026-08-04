package xyz.bluspring.modernnetworking.fabric.api.v2

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.network.FriendlyByteBuf
import xyz.bluspring.modernnetworking.api.minecraft.v2.PacketDefinitionHelpers.identifier
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket

/**
 * Some utilities to interact with Fabric's [net.fabricmc.fabric.api.networking.v1.PacketSender].
 */
object FabricPacketSender {
    /**
     * In the event you need to send a packet to a [net.fabricmc.fabric.api.networking.v1.PacketSender] directly, this will be very useful.
     */
    fun <T : NetworkPacket> PacketSender.sendPacket(packet: T) {
        val buf = PacketByteBufs.create()
        (packet.definition.codec as NetworkCodec<FriendlyByteBuf, T>).encode(buf, packet)
        this.sendPacket(packet.definition.identifier, buf)
    }
}
