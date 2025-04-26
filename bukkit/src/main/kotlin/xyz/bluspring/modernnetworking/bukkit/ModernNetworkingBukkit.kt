package xyz.bluspring.modernnetworking.bukkit

import org.bukkit.plugin.java.JavaPlugin
import xyz.bluspring.modernnetworking.bukkit.api.RegisterNetworkRegistryEvent

class ModernNetworkingBukkit : JavaPlugin() {
    override fun onEnable() {
        this.server.pluginManager.callEvent(RegisterNetworkRegistryEvent())
    }
}