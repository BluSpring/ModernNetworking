package xyz.bluspring.modernnetworking.api.v2.packet

interface SingleReceiverPacketHandlerRegistry<C> : PacketHandlerRegistry<C, Unit> {
    fun <T : NetworkPacket> send(packet: T) {
        this.send(Unit, packet)
    }

    override fun <T : NetworkPacket> send(receiver: Unit, packet: T) {
        this.send(packet)
    }
}
