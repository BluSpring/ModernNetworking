package xyz.bluspring.modernnetworking.minecraft

//? if >= 1.20.5 {
/*import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket

class CustomPayloadWrapper<T : NetworkPacket>(val registry: MinecraftPacketRegistry, val packet: T) : CustomPacketPayload {
    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return this.registry.getOrCreateType(packet.definition).type
    }
}
*///? }
