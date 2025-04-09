package xyz.bluspring.modernnetworking.modern

//? if >= 1.20.6 {
/*import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import xyz.bluspring.modernnetworking.api.NetworkPacket

class CustomPayloadWrapper<B : NetworkPacket>(private val type: CustomPacketPayload.Type<out CustomPacketPayload>, val packet: B) : CustomPacketPayload {
    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return type
    }
}
*///?}