@file:Suppress("DEPRECATION_ERROR")
package xyz.bluspring.modernnetworking.velocity.api

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.ServerConnection

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Rewritten to utilize the new v2 API")
data class VelocityNetworkContext(
    val player: Player,
    val proxy: ProxyServer,
    val server: ServerConnection
)
