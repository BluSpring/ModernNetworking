package xyz.bluspring.modernnetworking.api.v2.packet.registry.handler

abstract class AbstractSingleReceiverPacketHandlerRegistry<C> : AbstractPacketHandlerRegistry<C, Unit>(),
    SingleReceiverPacketHandlerRegistry<C> {
}
