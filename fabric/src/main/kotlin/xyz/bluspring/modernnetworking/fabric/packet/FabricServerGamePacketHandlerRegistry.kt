package xyz.bluspring.modernnetworking.fabric.packet

//? if >= 1.20.5 {
/*import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper
*///? } else {
import net.minecraft.network.FriendlyByteBuf
import xyz.bluspring.modernnetworking.minecraft.api.v2.PacketDefinitionHelpers.identifier
import io.netty.buffer.Unpooled
//? }
import io.netty.buffer.ByteBuf
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.level.ServerPlayer
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.context.ServerGamePacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.MinecraftPacketHandlerRegistry

class FabricServerGamePacketHandlerRegistry : MinecraftPacketHandlerRegistry<ServerGamePacketContext, ServerPlayer>(MinecraftPacketRegistries.CLIENT_PLAY) {
    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ServerGamePacketContext>) {
        //? if >= 1.20.5 {
        /*ServerPlayNetworking.registerGlobalReceiver(MinecraftPacketRegistries.SERVER_PLAY.getOrCreateType(definition).type) { packet, ctx ->
            ctx.server().execute {
                handler.handle(packet.packet, ServerGamePacketContext(ctx.player(), ctx.server()))
            }
        }
        *///? } else {
        ServerPlayNetworking.registerGlobalReceiver(definition.identifier) { server, player, listener, buf, sender ->
            val packet = definition.codec.cast<FriendlyByteBuf, T>().decode(buf)
            server.execute {
                handler.handle(packet, ServerGamePacketContext(player, server))
            }
        }
        //? }
    }

    override fun <T : NetworkPacket> send(receiver: ServerPlayer, packet: T) {
        //? if >= 1.20.5 {
        /*ServerPlayNetworking.send(receiver, CustomPayloadWrapper(this.opposingPacketRegistry, packet))
        *///? } else {
        val buf = FriendlyByteBuf(Unpooled.buffer())
        packet.definition.codec.cast<FriendlyByteBuf, T>().encode(buf, packet)
        ServerPlayNetworking.send(receiver, packet.definition.identifier, buf)
        //? }
    }
}
