package xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client

import io.netty.buffer.ByteBuf
import net.minecraft.network.FriendlyByteBuf
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.ServerLoginPacketHandlerRegistry.UnknownPacketHandler
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.SingleReceiverPacketHandlerRegistry
import java.util.concurrent.CompletableFuture

abstract class ClientLoginPacketHandlerRegistry<C> : SingleReceiverPacketHandlerRegistry<C> {
    abstract fun <B : ByteBuf, T : NetworkPacket> registerLogin(definition: PacketDefinition<B, T>, handler: LoginPacketHandler<T, C>)

    @Deprecated(message = "DO NOT USE THIS!", replaceWith = ReplaceWith("registerLogin"), level = DeprecationLevel.HIDDEN)
    final override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, C>) {
        throw IllegalStateException("Do not use register for client login packets!")
    }

    override fun <T : NetworkPacket> send(packet: T) {
        throw IllegalStateException("Do not use send for client login packets!")
    }

    fun interface LoginPacketHandler<T : NetworkPacket, C> {
        fun handle(packet: T, context: C): CompletableFuture<NetworkPacket?>
    }
}
