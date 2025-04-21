package xyz.bluspring.modernnetworking.modern

//? if >= 1.20.2 {
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import xyz.bluspring.modernnetworking.api.NetworkCodec
import xyz.bluspring.modernnetworking.api.NetworkPacket
//?}

//? if >= 1.20.6 {
class CustomPayloadWrapper<B : NetworkPacket>(private val type: CustomPacketPayload.Type<out CustomPacketPayload>, val packet: B) : CustomPacketPayload {
    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return type
    }
}
//?} else if >= 1.20.2 {
/*class CustomPayloadWrapper<B : NetworkPacket>(val packet: B) : CustomPacketPayload {
    override fun id(): ResourceLocation {
        return ResourceLocation(packet.definition.namespace, packet.definition.id)
    }

    override fun write(buffer: FriendlyByteBuf) {
        (packet.definition.codec as NetworkCodec<B, FriendlyByteBuf>).encode(buffer, this.packet)
    }
}
*///?}