package xyz.bluspring.modernnetworking.fabric.packet.client

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking
import net.minecraft.network.FriendlyByteBuf
import xyz.bluspring.modernnetworking.api.minecraft.v2.PacketDefinitionHelpers.identifier
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.ClientLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientLoginPacketContext
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition

class FabricClientLoginPacketHandlerRegistry : ClientLoginPacketHandlerRegistry<ClientLoginPacketContext>() {
    override fun <B : ByteBuf, T : NetworkPacket> registerLogin(definition: PacketDefinition<B, T>, handler: LoginPacketHandler<T, ClientLoginPacketContext>) {
        ClientLoginNetworking.registerGlobalReceiver(definition.identifier) { client, listener, buf, consumers ->
            val packet = definition.codec.cast<FriendlyByteBuf, T>().decode(buf)
            handler.handle(packet, ClientLoginPacketContext(listener, client))
                .thenApply { packet ->
                    if (packet != null) {
                        val friendlyBuf = FriendlyByteBuf(Unpooled.buffer())
                        (packet.definition.codec.cast<FriendlyByteBuf, NetworkPacket>())
                            .encode(friendlyBuf, packet)
                        friendlyBuf
                    } else null
                }
        }
    }
}
