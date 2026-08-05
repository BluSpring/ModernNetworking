package xyz.bluspring.modernnetworking.velocity.api.v2

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.ServerConnection

data class VelocityPacketContext(
    val player: Player,
    val proxy: ProxyServer,
    val connection: ServerConnection,
)
