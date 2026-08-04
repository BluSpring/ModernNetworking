package xyz.bluspring.modernnetworking.bukkit

import org.bukkit.plugin.java.JavaPlugin
import xyz.bluspring.modernnetworking.bukkit.api.RegisterNetworkRegistryEvent

class ModernNetworkingBukkit : JavaPlugin() {
    override fun onEnable() {
        // v1
        this.server.pluginManager.callEvent(RegisterNetworkRegistryEvent())

        plugin = this
    }

    companion object {
        lateinit var plugin: ModernNetworkingBukkit
        val supportsConfiguration: Boolean by lazy {
            val mcVersion = this.plugin.server.minecraftVersion

            // legacy snapshot versioning
            if (mcVersion.contains("w")) {
                val components = mcVersion.split("w").mapNotNull { it.split("-")[0].filter { b -> b.isDigit() }.toIntOrNull() }
                if (components.isEmpty())
                    return@lazy false

                return@lazy if (components[0] == 23)
                    components[1] >= 31 // >=23w31a
                else components[0] > 23 // >= 2024 snapshots
            }

            val components = mcVersion.split(".").mapNotNull { it.split("-")[0].filter { b -> b.isDigit() }.toIntOrNull() }
            if (components.isEmpty())
                return@lazy false

            if (components[0] > 1) { // 26.1+
                true
            } else if (components[1] == 20) { // 1.20.x
                (components.getOrNull(2) ?: 0) >= 5 // >= 1.20.5
            } else components[1] > 20 // >= 1.21
        }
    }
}
