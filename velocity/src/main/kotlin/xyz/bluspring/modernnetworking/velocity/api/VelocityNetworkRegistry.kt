@file:Suppress("DEPRECATION_ERROR")
package xyz.bluspring.modernnetworking.velocity.api

import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import io.netty.buffer.ByteBuf
import org.jetbrains.annotations.ApiStatus
import xyz.bluspring.modernnetworking.api.AbstractNetworkRegistry
import xyz.bluspring.modernnetworking.api.NetworkPacket
import xyz.bluspring.modernnetworking.api.PacketDefinition
import xyz.bluspring.modernnetworking.internal.WrappedV1AsV2NetworkPacket
import xyz.bluspring.modernnetworking.velocity.api.v2.VelocityPacketHandlerRegistries
import xyz.bluspring.modernnetworking.velocity.api.v2.VelocityPacketRegistries
import java.util.Collections

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Rewritten to utilize the new v2 API")
class VelocityNetworkRegistry(private val proxy: ProxyServer, namespace: String) : AbstractNetworkRegistry<VelocityNetworkContext, VelocityNetworkContext>(namespace) {
    override fun <T : NetworkPacket, B : ByteBuf> registerClientbound(definition: PacketDefinition<T, B>): PacketDefinition<T, B> {
        VelocityPacketRegistries.clientPlayRegistry.register(definition.asV2)
        return super.registerClientbound(definition)
    }

    override fun <T : NetworkPacket, B : ByteBuf> registerServerbound(definition: PacketDefinition<T, B>): PacketDefinition<T, B> {
        VelocityPacketRegistries.serverPlayRegistry.register(definition.asV2)
        return super.registerServerbound(definition)
    }

    override fun <T : NetworkPacket, B : ByteBuf> addClientboundHandler(definition: PacketDefinition<T, B>, handler: NetworkHandler<VelocityNetworkContext, T>) {
        super.addClientboundHandler(definition, handler)
        VelocityPacketHandlerRegistries.CLIENT_PLAY.register(definition.asV2) { packet, ctx ->
            handler.handle(packet.original, VelocityNetworkContext(ctx.player, ctx.proxy, ctx.connection))
        }
    }

    override fun <T : NetworkPacket, B : ByteBuf> addServerboundHandler(definition: PacketDefinition<T, B>, handler: NetworkHandler<VelocityNetworkContext, T>) {
        super.addServerboundHandler(definition, handler)
        VelocityPacketHandlerRegistries.SERVER_PLAY.register(definition.asV2) { packet, ctx ->
            handler.handle(packet.original, VelocityNetworkContext(ctx.player, ctx.proxy, ctx.connection))
        }
    }

    companion object {
        val registries: MutableList<VelocityNetworkRegistry> = Collections.synchronizedList(mutableListOf())

        @ApiStatus.Internal
        @JvmStatic
        fun get(namespace: String): VelocityNetworkRegistry? {
            synchronized(registries) {
                return registries.firstOrNull { it.namespace == namespace }
            }
        }
    }
}
