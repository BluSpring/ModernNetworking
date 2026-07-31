package xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context

import net.minecraft.server.MinecraftServer

abstract class ServerCommonPacketContext<T>(
    val handler: T,
    val server: MinecraftServer,
)
