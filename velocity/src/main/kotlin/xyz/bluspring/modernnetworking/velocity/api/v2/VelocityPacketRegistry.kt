package xyz.bluspring.modernnetworking.velocity.api.v2

import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.DefaultedPacketRegistry

class VelocityPacketRegistry(private val proxy: ProxyServer) : DefaultedPacketRegistry() {
    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>): PacketDefinition<B, T> {
        this.proxy.channelRegistrar.register(MinecraftChannelIdentifier.create(definition.namespace, definition.id))
        return super.register(definition)
    }
}
