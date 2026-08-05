package xyz.bluspring.modernnetworking.api.minecraft

import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Refactored to provide further context information.", replaceWith = ReplaceWith("ServerCommonPacketContext", "xyz.bluspring.modernnetworking.minecraft.api.context.packet.v2.ServerCommonPacketContext"))
data class VanillaServerContext(
    val server: MinecraftServer,
    val player: ServerPlayer
)
