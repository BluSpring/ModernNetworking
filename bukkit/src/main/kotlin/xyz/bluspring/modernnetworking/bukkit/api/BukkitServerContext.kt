package xyz.bluspring.modernnetworking.bukkit.api

import org.bukkit.Server
import org.bukkit.entity.Player

data class BukkitServerContext(
    val server: Server,
    val player: Player
)
