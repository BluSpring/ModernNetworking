package xyz.bluspring.modernnetworking.bukkit.api

import org.bukkit.Server
import org.bukkit.entity.Player

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Rewritten to use a significantly improved network system")
data class BukkitServerContext(
    val server: Server,
    val player: Player
)
