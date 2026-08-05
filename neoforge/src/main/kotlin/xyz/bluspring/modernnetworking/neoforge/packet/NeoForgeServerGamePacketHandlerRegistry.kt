package xyz.bluspring.modernnetworking.neoforge.packet

import net.minecraft.network.ConnectionProtocol
import net.minecraft.network.protocol.PacketFlow
import net.minecraft.server.level.ServerPlayer
import net.neoforged.neoforge.network.handling.IPayloadContext
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.context.ServerGamePacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper

class NeoForgeServerGamePacketHandlerRegistry : NeoForgePacketHandlerRegistry<ServerGamePacketContext, ServerPlayer>(
    MinecraftPacketRegistries.SERVER_PLAY, MinecraftPacketRegistries.CLIENT_PLAY, PacketFlow.SERVERBOUND, ConnectionProtocol.PLAY
) {
    override fun createPayloadContext(context: IPayloadContext): ServerGamePacketContext {
        // this is so hacky oml
        val player = context.player()
            //? if <= 1.20.4
            //.orElseThrow()
        return ServerGamePacketContext(player as ServerPlayer,
            //? if <= 1.21.8 {
            /*player.server!!
            *///? } else {
            player.level().server
            //? }
        )
    }

    override fun <T : NetworkPacket> send(receiver: ServerPlayer, packet: T) {
        receiver.connection.send(CustomPayloadWrapper(this.packetRegistry, packet))
    }
}
