package xyz.bluspring.modernnetworking.neoforge.packet.client

import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.ConnectionProtocol
import net.minecraft.network.protocol.PacketFlow
import net.neoforged.neoforge.network.handling.IPayloadContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientGamePacketContext

class NeoForgeClientGamePacketHandlerRegistry : NeoForgeClientPacketHandlerRegistry<ClientGamePacketContext>(
    MinecraftPacketRegistries.CLIENT_PLAY, MinecraftPacketRegistries.SERVER_PLAY, PacketFlow.CLIENTBOUND, ConnectionProtocol.PLAY
) {
    override fun createPayloadContext(context: IPayloadContext): ClientGamePacketContext {
        // this is so hacky oml
        return ClientGamePacketContext(context.player() as LocalPlayer, Minecraft.getInstance())
    }
}
