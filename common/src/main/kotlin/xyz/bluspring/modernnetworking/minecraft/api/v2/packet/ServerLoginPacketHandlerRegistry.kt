package xyz.bluspring.modernnetworking.minecraft.api.v2.packet

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.packet.DualPacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry

abstract class ServerLoginPacketHandlerRegistry<C, R> : PacketHandlerRegistry<C, R> {
    fun <B : ByteBuf, T : NetworkPacket> registerLogin(definition: DualPacketDefinition<B, T, *>, handler: PacketHandlerRegistry.PacketHandler<T, C>, unknownHandler: UnknownPacketHandler<C>) {
        this.registerLogin(definition.serverbound, handler, unknownHandler)
    }

    abstract fun <B : ByteBuf, T : NetworkPacket> registerLogin(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, C>, unknownHandler: UnknownPacketHandler<C>)

    @Deprecated(message = "DO NOT USE THIS!", replaceWith = ReplaceWith("registerLogin"), level = DeprecationLevel.HIDDEN)
    final override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, C>) {
        throw IllegalStateException("Do not use register for server login packets!")
    }

    @Deprecated(message = "DO NOT USE THIS!", level = DeprecationLevel.HIDDEN)
    override fun <T : NetworkPacket> send(receiver: R, packet: T) {
        throw IllegalStateException("Do not use send for server login packets!")
    }

    fun interface UnknownPacketHandler<C> {
        fun handle(context: C)
    }
}
