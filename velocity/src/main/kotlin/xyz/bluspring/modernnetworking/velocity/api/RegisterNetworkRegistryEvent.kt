@file:Suppress("DEPRECATION_ERROR")
package xyz.bluspring.modernnetworking.velocity.api

import com.velocitypowered.api.event.annotation.AwaitingEvent
import com.velocitypowered.api.proxy.ProxyServer

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Rewritten to utilize the new v2 API")
@AwaitingEvent
class RegisterNetworkRegistryEvent(private val proxy: ProxyServer) {
    fun create(namespace: String): VelocityNetworkRegistry {
        return VelocityNetworkRegistry(proxy, namespace)
    }
}
