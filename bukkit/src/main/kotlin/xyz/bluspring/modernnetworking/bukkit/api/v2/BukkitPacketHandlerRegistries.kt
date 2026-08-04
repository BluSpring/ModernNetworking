package xyz.bluspring.modernnetworking.bukkit.api.v2

import org.bukkit.entity.Player
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.bukkit.BukkitPacketHandlerRegistry

object BukkitPacketHandlerRegistries {
    @JvmField val LOGIN: PacketHandlerRegistry<BukkitPacketContext, Player> = BukkitPacketHandlerRegistry()
    @JvmField val CONFIGURATION: PacketHandlerRegistry<BukkitPacketContext, Player> = BukkitPacketHandlerRegistry()
    @JvmField val PLAY: PacketHandlerRegistry<BukkitPacketContext, Player> = BukkitPacketHandlerRegistry()
}
