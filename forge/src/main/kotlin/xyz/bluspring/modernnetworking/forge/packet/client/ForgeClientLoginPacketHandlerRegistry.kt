package xyz.bluspring.modernnetworking.forge.packet.client

import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import net.minecraftforge.network.NetworkRegistry
import xyz.bluspring.modernnetworking.minecraft.api.v2.PacketDefinitionHelpers.identifier
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.client.ClientLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.client.context.ClientLoginPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition

class ForgeClientLoginPacketHandlerRegistry : ClientLoginPacketHandlerRegistry<ClientLoginPacketContext>() {
    override fun <B : ByteBuf, T : NetworkPacket> registerLogin(definition: PacketDefinition<B, T>, handler: LoginPacketHandler<T, ClientLoginPacketContext>) {
        val channel = NetworkRegistry.newEventChannel(definition.identifier, { "1" }, { true }, { true })
        channel.addListener<NetworkEvent.ClientCustomPayloadLoginEvent> { event ->
            val ctx = event.source.get()
            val client = Minecraft.getInstance()

            val packetCtx = ClientLoginPacketContext(ctx.networkManager.packetListener as ClientHandshakePacketListenerImpl, client)
            val buf = event.payload!!
            val packet = definition.codec.cast<FriendlyByteBuf, T>().decode(buf)
            handler.handle(packet, packetCtx)

            ctx.packetHandled = true
        }
    }
}
