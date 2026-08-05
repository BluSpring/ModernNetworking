package xyz.bluspring.modernnetworking.minecraft.api.v2.packet.context

import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.network.ServerGamePacketListenerImpl

class ServerGamePacketContext(
    val player: ServerPlayer,
    server: MinecraftServer,
) : ServerCommonPacketContext<ServerGamePacketListenerImpl>(player.connection, server)
