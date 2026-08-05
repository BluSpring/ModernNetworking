@file:Suppress("DEPRECATION_ERROR")
package xyz.bluspring.modernnetworking.api.minecraft

import xyz.bluspring.modernnetworking.api.NetworkPacket
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.client.MinecraftClientPacketHandlers
import xyz.bluspring.modernnetworking.internal.WrappedV1AsV2NetworkPacket

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Refactored to provide access to more phases i.e. login and configuration phases.")
object VanillaPacketSenderClient {
    /**
     * Sends a packet from the client to the server.
     */
    @JvmStatic
    fun sendToServer(packet: NetworkPacket) {
        MinecraftClientPacketHandlers.PLAY.send(WrappedV1AsV2NetworkPacket(packet))
    }
}
