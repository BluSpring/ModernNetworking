package xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context

import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerLoginPacketListenerImpl
import java.util.concurrent.Future

class ServerLoginPacketContext(
    handler: ServerLoginPacketListenerImpl,
    server: MinecraftServer,
    val wasUnderstood: Boolean,
    val synchronizer: LoginSynchronizer,
) : ServerCommonPacketContext<ServerLoginPacketListenerImpl>(handler, server) {
    fun interface LoginSynchronizer {
        fun waitFor(future: Future<*>)
    }
}
