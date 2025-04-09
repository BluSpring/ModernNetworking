package xyz.bluspring.modernnetworking.api

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.Minecraft
//? if < 1.20.6 {
import io.netty.buffer.Unpooled
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
//?}
import net.minecraft.network.protocol./*? if < 1.20.6 {*/game/*?} else {*//*common*//*?}*/.ClientboundCustomPayloadPacket
import net.minecraft.network.protocol./*? if < 1.20.6 {*/game/*?} else {*//*common*//*?}*/.ServerboundCustomPayloadPacket
import net.minecraft.server.level.ServerPlayer
import xyz.bluspring.modernnetworking.modern.CustomPayloadWrapper

object VanillaPacketSender {
    /**
     * Sends a packet from the client to the server.
     */
    @Environment(EnvType.CLIENT)
    @JvmStatic
    fun sendToServer(packet: NetworkPacket) {
        //? if < 1.20.6 {
        val buf = FriendlyByteBuf(Unpooled.buffer())
        (packet.definition.codec as NetworkCodec<NetworkPacket, FriendlyByteBuf>).encode(buf, packet)
        Minecraft.getInstance().connection!!.send(ServerboundCustomPayloadPacket(ResourceLocation(packet.definition.namespace, packet.definition.id), buf))
        //?} else {
        
        /*val registry = VanillaNetworkRegistry.get(packet.definition.namespace) ?: throw IllegalArgumentException("Packet namespace ${packet.definition.namespace} does not exist! ($packet, ${packet.definition})")
        Minecraft.getInstance().connection!!.send(ServerboundCustomPayloadPacket(CustomPayloadWrapper(registry.serverTypes[packet.definition.id]!!, packet)))

        *///?}
    }

    /**
     * Sends a packet from the server to the client.
     */
    @JvmStatic
    fun sendToPlayer(player: ServerPlayer, packet: NetworkPacket) {
        //? if < 1.20.6 {
        val buf = FriendlyByteBuf(Unpooled.buffer())
        (packet.definition.codec as NetworkCodec<NetworkPacket, FriendlyByteBuf>).encode(buf, packet)
        player.connection.send(ClientboundCustomPayloadPacket(ResourceLocation(packet.definition.namespace, packet.definition.id), buf))
        //?} else {

        /*val registry = VanillaNetworkRegistry.get(packet.definition.namespace) ?: throw IllegalArgumentException("Packet namespace ${packet.definition.namespace} does not exist! ($packet, ${packet.definition})")
        player.connection.send(ClientboundCustomPayloadPacket(CustomPayloadWrapper(registry.clientTypes[packet.definition.id]!!, packet)))

        *///?}
    }
}