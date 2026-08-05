package xyz.bluspring.modernnetworking.forge.packet.client

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket
import net.minecraftforge.network.NetworkEvent
import net.minecraftforge.network.event.EventNetworkChannel
import xyz.bluspring.modernnetworking.minecraft.api.v2.PacketDefinitionHelpers.identifier
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.client.context.ClientGamePacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.SingleReceiverPacketHandlerRegistry
import xyz.bluspring.modernnetworking.forge.packet.ForgePacketHandlerRegistry

class ForgeClientGamePacketHandlerRegistry : ForgePacketHandlerRegistry<ClientGamePacketContext, Unit>(), SingleReceiverPacketHandlerRegistry<ClientGamePacketContext> {
    override fun <B : ByteBuf, T : NetworkPacket> setupEventChannel(channel: EventNetworkChannel, definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ClientGamePacketContext>) {
        channel.addListener<NetworkEvent.ClientCustomPayloadEvent> { event ->
            val packet = definition.codec.cast<FriendlyByteBuf, T>().decode(event.payload)
            val ctx = event.source.get()
            val client = Minecraft.getInstance()
            ctx.enqueueWork {
                handler.handle(packet, ClientGamePacketContext(client.player!!, client))
            }
            
            ctx.packetHandled = true
        }
    }

    override fun <T : NetworkPacket> send(packet: T) {
        val buf = FriendlyByteBuf(Unpooled.buffer())
        packet.definition.codec.cast<FriendlyByteBuf, T>().encode(buf, packet)

        Minecraft.getInstance().connection!!.send(ServerboundCustomPayloadPacket(packet.definition.identifier, buf))
    }
}
