package xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context

//? if >= 1.20.2 {
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl

class ServerConfigurationPacketContext(
    handler: ServerConfigurationPacketListenerImpl,
    server: MinecraftServer,
) : ServerCommonPacketContext<ServerConfigurationPacketListenerImpl>(handler, server) {
}
//? }
