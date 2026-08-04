package xyz.bluspring.modernnetworking.api.v2.packet.registry.handler

import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket

/**
 * The same as [PacketHandlerRegistry], except the receiver is [Unit] for when there is only ever going to be one receiver on this handler.
 */
interface SingleReceiverPacketHandlerRegistry<C> : PacketHandlerRegistry<C, Unit> {
    fun <T : NetworkPacket> send(packet: T) {
        this.send(Unit, packet)
    }

    override fun <T : NetworkPacket> send(receiver: Unit, packet: T) {
        this.send(packet)
    }
}
