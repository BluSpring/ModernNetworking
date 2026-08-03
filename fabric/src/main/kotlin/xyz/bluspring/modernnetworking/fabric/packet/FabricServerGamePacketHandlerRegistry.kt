package xyz.bluspring.modernnetworking.fabric.packet

//? if >= 1.20.5 {
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper
//? } else {
/*import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.network.FriendlyByteBuf
import xyz.bluspring.modernnetworking.api.minecraft.v2.PacketDefinitionHelpers.identifier
*///? }
import io.netty.buffer.ByteBuf
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.level.ServerPlayer
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerGamePacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.MinecraftPacketHandlerRegistry

class FabricServerGamePacketHandlerRegistry : MinecraftPacketHandlerRegistry<ServerGamePacketContext, ServerPlayer>(MinecraftPacketRegistries.SERVER_PLAY) {
    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ServerGamePacketContext>) {
        //? if >= 1.20.5 {
        val type = this.packetRegistry.getOrCreateType(definition)
        PayloadTypeRegistry.playC2S().register(type.type, type.codec)
        //? } else {
        /*ServerPlayNetworking.registerGlobalReceiver(definition.identifier) { server, player, listener, buf, sender ->
            val packet = definition.codec.decode(buf as B)
            handler.handle(packet, ServerGamePacketContext(player, server))
        }
        *///? }
    }

    override fun <T : NetworkPacket> send(receiver: ServerPlayer, packet: T) {
        //? if >= 1.20.5 {
        ServerPlayNetworking.send(receiver, CustomPayloadWrapper(this.packetRegistry, packet))
        //? } else {
        /*val buf = PacketByteBufs.create()
        (packet.definition as PacketDefinition<FriendlyByteBuf, T>).codec.encode(buf, packet)
        ServerPlayNetworking.send(receiver, packet.definition.identifier, buf)
        *///? }
    }
}
