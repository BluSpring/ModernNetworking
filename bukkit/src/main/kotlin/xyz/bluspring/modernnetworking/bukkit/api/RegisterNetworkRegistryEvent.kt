package xyz.bluspring.modernnetworking.bukkit.api

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.plugin.Plugin

class RegisterNetworkRegistryEvent : Event() {
    fun create(plugin: Plugin): BukkitNetworkRegistry {
        return BukkitNetworkRegistry(plugin)
    }

    fun create(plugin: Plugin, namespace: String): BukkitNetworkRegistry {
        return BukkitNetworkRegistry(plugin, namespace)
    }

    override fun getHandlers(): HandlerList {
        return Companion.handlers
    }

    companion object {
        private val handlers = HandlerList()
    }
}