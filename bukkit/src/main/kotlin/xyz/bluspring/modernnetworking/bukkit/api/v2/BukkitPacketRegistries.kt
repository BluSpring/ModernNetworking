package xyz.bluspring.modernnetworking.bukkit.api.v2

import xyz.bluspring.modernnetworking.api.v2.packet.registry.DefaultedPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.PacketRegistry
import xyz.bluspring.modernnetworking.bukkit.BukkitClientPacketRegistry

object BukkitPacketRegistries {
    @JvmField val SERVER_LOGIN: PacketRegistry = DefaultedPacketRegistry()
    @JvmField val SERVER_CONFIGURATION: PacketRegistry = DefaultedPacketRegistry()
    @JvmField val SERVER_PLAY: PacketRegistry = DefaultedPacketRegistry()

    @JvmField val CLIENT_LOGIN: PacketRegistry = BukkitClientPacketRegistry()
    @JvmField val CLIENT_CONFIGURATION: PacketRegistry = BukkitClientPacketRegistry()
    @JvmField val CLIENT_PLAY: PacketRegistry = BukkitClientPacketRegistry()
}
