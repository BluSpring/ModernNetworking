package xyz.bluspring.modernnetworking.velocity.api

import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import io.netty.buffer.ByteBuf
import org.jetbrains.annotations.ApiStatus
import xyz.bluspring.modernnetworking.api.AbstractNetworkRegistry
import xyz.bluspring.modernnetworking.api.NetworkPacket
import xyz.bluspring.modernnetworking.api.PacketDefinition
import java.util.Collections

class VelocityNetworkRegistry(private val proxy: ProxyServer, namespace: String) : AbstractNetworkRegistry<VelocityNetworkContext, VelocityNetworkContext>(namespace) {
    override fun <T : NetworkPacket, B : ByteBuf> registerClientbound(definition: PacketDefinition<T, B>): PacketDefinition<T, B> {
        proxy.channelRegistrar.register(MinecraftChannelIdentifier.create(definition.namespace, definition.id))
        return super.registerClientbound(definition)
    }

    override fun <T : NetworkPacket, B : ByteBuf> registerServerbound(definition: PacketDefinition<T, B>): PacketDefinition<T, B> {
        proxy.channelRegistrar.register(MinecraftChannelIdentifier.create(definition.namespace, definition.id))
        return super.registerClientbound(definition)
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