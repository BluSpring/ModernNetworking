package xyz.bluspring.modernnetworking.velocity.api

import com.velocitypowered.api.event.annotation.AwaitingEvent
import com.velocitypowered.api.proxy.ProxyServer

@AwaitingEvent
class RegisterNetworkRegistryEvent(private val proxy: ProxyServer) {
    fun create(namespace: String): VelocityNetworkRegistry {
        return VelocityNetworkRegistry(proxy, namespace)
    }
}