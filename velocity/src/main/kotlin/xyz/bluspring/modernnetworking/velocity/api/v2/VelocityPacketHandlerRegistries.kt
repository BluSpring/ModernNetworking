package xyz.bluspring.modernnetworking.velocity.api.v2

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ServerConnection
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.velocity.VelocityPacketHandlerRegistry

object VelocityPacketHandlerRegistries {
    @JvmField val SERVER_PLAY: PacketHandlerRegistry<VelocityPacketContext, ServerConnection> = VelocityPacketHandlerRegistry()
    @JvmField val CLIENT_PLAY: PacketHandlerRegistry<VelocityPacketContext, Player> = VelocityPacketHandlerRegistry()
}
