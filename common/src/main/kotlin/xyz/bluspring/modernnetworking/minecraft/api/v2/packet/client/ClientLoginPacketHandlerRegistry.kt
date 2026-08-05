package xyz.bluspring.modernnetworking.minecraft.api.v2.packet.client

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.packet.DualPacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.SingleReceiverPacketHandlerRegistry
import java.util.concurrent.CompletableFuture

abstract class ClientLoginPacketHandlerRegistry<C> : SingleReceiverPacketHandlerRegistry<C> {
    fun <B : ByteBuf, T : NetworkPacket> registerLogin(definition: DualPacketDefinition<B, *, T>, handler: LoginPacketHandler<T, C>) {
        this.registerLogin(definition.clientbound, handler)
    }

    abstract fun <B : ByteBuf, T : NetworkPacket> registerLogin(definition: PacketDefinition<B, T>, handler: LoginPacketHandler<T, C>)

    @Deprecated(message = "DO NOT USE THIS!", replaceWith = ReplaceWith("registerLogin"), level = DeprecationLevel.HIDDEN)
    final override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, C>) {
        throw IllegalStateException("Do not use register for client login packets!")
    }

    @Deprecated(message = "DO NOT USE THIS!", level = DeprecationLevel.HIDDEN)
    override fun <T : NetworkPacket> send(packet: T) {
        throw IllegalStateException("Do not use send for client login packets!")
    }

    fun interface LoginPacketHandler<T : NetworkPacket, C> {
        fun handle(packet: T, context: C): CompletableFuture<NetworkPacket?>
    }
}
