package xyz.bluspring.modernnetworking.api.minecraft

import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

data class VanillaServerContext(
    val server: MinecraftServer,
    val player: ServerPlayer
)
