package xyz.bluspring.modernnetworking.api.v2.packet

import io.netty.buffer.ByteBuf

abstract class AbstractSingleReceiverPacketHandlerRegistry<C> : AbstractPacketHandlerRegistry<C, Unit>(), SingleReceiverPacketHandlerRegistry<C> {
}
