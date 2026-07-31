package xyz.bluspring.modernnetworking.fabric.packet.client

//? if >= 1.20.2 {
/*import io.netty.buffer.ByteBuf
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientConfigurationPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper
import xyz.bluspring.modernnetworking.minecraft.MinecraftSingleReceiverPacketHandlerRegistry

class FabricClientConfigurationPacketHandlerRegistry : MinecraftSingleReceiverPacketHandlerRegistry<ClientConfigurationPacketContext>(MinecraftPacketRegistries.CLIENT_PLAY) {
    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ClientConfigurationPacketContext>) {
        ClientConfigurationNetworking.registerGlobalReceiver(this.packetRegistry.getOrCreateType(definition).type) { packet, ctx ->
            handler.handle(packet.packet, ClientConfigurationPacketContext(ctx.networkHandler(), ctx.client()))
        }
    }

    override fun <T : NetworkPacket> send(packet: T) {
        ClientConfigurationNetworking.send(CustomPayloadWrapper(this.packetRegistry, packet))
    }
}
*///? }
