@file:Suppress("DEPRECATION_ERROR")
package xyz.bluspring.modernnetworking.api.minecraft

import net.minecraft.server.level.ServerPlayer
import xyz.bluspring.modernnetworking.api.NetworkPacket
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.MinecraftServerPacketHandlers
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.client.MinecraftClientPacketHandlers
import xyz.bluspring.modernnetworking.internal.WrappedV1AsV2NetworkPacket

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Refactored to provide access to more phases i.e. login and configuration phases.")
object VanillaPacketSender {
    /**
     * Sends a packet from the client to the server.
     */
    @JvmStatic
    fun sendToServer(packet: NetworkPacket) {
        MinecraftClientPacketHandlers.PLAY.send(WrappedV1AsV2NetworkPacket(packet))
    }

    /**
     * Sends a packet from the server to the client.
     */
    @JvmStatic
    fun sendToPlayer(player: ServerPlayer, packet: NetworkPacket) {
        MinecraftServerPacketHandlers.PLAY.send(player, WrappedV1AsV2NetworkPacket(packet))
    }
}
