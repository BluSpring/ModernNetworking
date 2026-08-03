package xyz.bluspring.modernnetworking.fabric.packet.client

//? if >= 1.20.5 {
/*import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper
*///? } else {
//? }
import io.netty.buffer.ByteBuf
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.network.FriendlyByteBuf
import xyz.bluspring.modernnetworking.api.minecraft.v2.PacketDefinitionHelpers.identifier
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientGamePacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.MinecraftSingleReceiverPacketHandlerRegistry

class FabricClientGamePacketHandlerRegistry : MinecraftSingleReceiverPacketHandlerRegistry<ClientGamePacketContext>(MinecraftPacketRegistries.CLIENT_PLAY) {
    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ClientGamePacketContext>) {
        //? if >= 1.20.5 {
        /*ClientPlayNetworking.registerGlobalReceiver(this.packetRegistry.getOrCreateType(definition).type) { packet, ctx ->
            handler.handle(packet.packet, ClientGamePacketContext(ctx.player(), ctx.client()))
        }
        *///? } else {
        ClientPlayNetworking.registerGlobalReceiver(definition.identifier) { client, listener, buf, sender ->
            val packet = definition.codec.decode(buf as B)
            handler.handle(packet, ClientGamePacketContext(client.player!!, client))
        }
        //? }
    }

    override fun <T : NetworkPacket> send(packet: T) {
        //? if >= 1.20.5 {
        /*ClientPlayNetworking.send(CustomPayloadWrapper(this.packetRegistry, packet))
        *///? } else {
        val buf = PacketByteBufs.create()
        (packet.definition as PacketDefinition<FriendlyByteBuf, T>).codec.encode(buf, packet)
        ClientPlayNetworking.send(packet.definition.identifier, buf)
        //? }
    }
}
