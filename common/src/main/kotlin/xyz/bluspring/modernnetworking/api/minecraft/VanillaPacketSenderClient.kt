package xyz.bluspring.modernnetworking.api.minecraft

import net.minecraft.client.Minecraft
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket
import xyz.bluspring.modernnetworking.api.NetworkPacket
import xyz.bluspring.modernnetworking.modern.CustomPayloadWrapper

object VanillaPacketSenderClient {
    /**
     * Sends a packet from the client to the server.
     */
    @JvmStatic
    fun sendToServer(packet: NetworkPacket) {
        //? if < 1.20.2 {
        /*val buf = FriendlyByteBuf(Unpooled.buffer())
        (packet.definition.codec as NetworkCodec<NetworkPacket, FriendlyByteBuf>).encode(buf, packet)
        Minecraft.getInstance().connection!!.send(ServerboundCustomPayloadPacket(ResourceLocation(packet.definition.namespace, packet.definition.id), buf))
        *///?} else {

        val registry = VanillaNetworkRegistry.get(packet.definition.namespace) ?: throw IllegalArgumentException("Packet namespace ${packet.definition.namespace} does not exist! ($packet, ${packet.definition})")
        Minecraft.getInstance().connection!!.send(ServerboundCustomPayloadPacket(CustomPayloadWrapper(/*? if >= 1.20.6 {*//*registry.serverTypes[packet.definition.id]!!,*//*?}*/ packet)))

        //?}
    }
}