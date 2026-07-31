package xyz.bluspring.modernnetworking.api.minecraft

import net.minecraft.server.level.ServerPlayer
import xyz.bluspring.modernnetworking.api.NetworkPacket
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftServerPacketHandlerRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.MinecraftClientPacketHandlerRegistries
import xyz.bluspring.modernnetworking.internal.WrappedV1AsV2NetworkPacket

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Refactored to provide access to more phases i.e. login and configuration phases.")
object VanillaPacketSender {
    /**
     * Sends a packet from the client to the server.
     */
    @JvmStatic
    fun sendToServer(packet: NetworkPacket) {
        MinecraftClientPacketHandlerRegistries.PLAY.send(WrappedV1AsV2NetworkPacket(packet))
    }

    /**
     * Sends a packet from the server to the client.
     */
    @JvmStatic
    fun sendToPlayer(player: ServerPlayer, packet: NetworkPacket) {
        MinecraftServerPacketHandlerRegistries.PLAY.send(player, WrappedV1AsV2NetworkPacket(packet))
    }
}
