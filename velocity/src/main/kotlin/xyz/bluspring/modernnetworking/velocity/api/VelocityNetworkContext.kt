package xyz.bluspring.modernnetworking.velocity.api

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.ServerConnection

data class VelocityNetworkContext(
    val player: Player,
    val proxy: ProxyServer,
    val server: ServerConnection
)
