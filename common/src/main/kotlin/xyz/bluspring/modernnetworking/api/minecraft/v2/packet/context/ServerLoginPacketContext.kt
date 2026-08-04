package xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context

import net.minecraft.network.protocol.Packet
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerLoginPacketListenerImpl
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import java.util.concurrent.Future

class ServerLoginPacketContext(
    handler: ServerLoginPacketListenerImpl,
    server: MinecraftServer,
    private val packetSender: (Packet<*>) -> Unit,
    val wasUnderstood: Boolean,
    val synchronizer: LoginSynchronizer,
) : ServerCommonPacketContext<ServerLoginPacketListenerImpl>(handler, server) {
    fun sendPacket(packet: Packet<*>) {
        this.packetSender(packet)
    }

    fun interface LoginSynchronizer {
        fun waitFor(future: Future<*>)
    }
}
