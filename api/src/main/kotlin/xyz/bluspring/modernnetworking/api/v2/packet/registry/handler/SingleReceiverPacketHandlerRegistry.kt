package xyz.bluspring.modernnetworking.api.v2.packet.registry.handler

import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket

interface SingleReceiverPacketHandlerRegistry<C> : PacketHandlerRegistry<C, Unit> {
    fun <T : NetworkPacket> send(packet: T) {
        this.send(Unit, packet)
    }

    override fun <T : NetworkPacket> send(receiver: Unit, packet: T) {
        this.send(packet)
    }
}
