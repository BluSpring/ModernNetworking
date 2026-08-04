package xyz.bluspring.modernnetworking.fabric.packet.client

//? if >= 1.20.5 {
/*import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper
*///? } else {
import net.minecraft.network.FriendlyByteBuf
import xyz.bluspring.modernnetworking.api.minecraft.v2.PacketDefinitionHelpers.identifier
//? }
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientGamePacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.MinecraftSingleReceiverPacketHandlerRegistry

class FabricClientGamePacketHandlerRegistry : MinecraftSingleReceiverPacketHandlerRegistry<ClientGamePacketContext>(MinecraftPacketRegistries.SERVER_PLAY) {
    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ClientGamePacketContext>) {
        //? if >= 1.20.5 {
        /*ClientPlayNetworking.registerGlobalReceiver(this.opposingPacketRegistry.getOrCreateType(definition).type) { packet, ctx ->
            handler.handle(packet.packet, ClientGamePacketContext(ctx.player(), ctx.client()))
        }
        *///? } else {
        ClientPlayNetworking.registerGlobalReceiver(definition.identifier) { client, listener, buf, sender ->
            val packet = definition.codec.cast<FriendlyByteBuf, T>().decode(buf)
            client.execute {
                handler.handle(packet, ClientGamePacketContext(client.player, client))
            }
        }
        //? }
    }

    override fun <T : NetworkPacket> send(packet: T) {
        //? if >= 1.20.5 {
        /*ClientPlayNetworking.send(CustomPayloadWrapper(this.opposingPacketRegistry, packet))
        *///? } else {
        val buf = FriendlyByteBuf(Unpooled.buffer())
        packet.definition.codec.cast<FriendlyByteBuf, T>().encode(buf, packet)
        ClientPlayNetworking.send(packet.definition.identifier, buf)
        //? }
    }
}
