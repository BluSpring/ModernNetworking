package xyz.bluspring.modernnetworking.minecraft

//? if >= 1.20.5 {
/*import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import xyz.bluspring.modernnetworking.minecraft.api.packet.v2.MinecraftPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket

class CustomPayloadWrapper<T : NetworkPacket>(val registry: MinecraftPacketRegistry, val packet: T) : CustomPacketPayload {
    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        return this.registry.getOrCreateType(packet.definition).type
    }
}
*///? } else if >= 1.20.2 {
/*import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import xyz.bluspring.modernnetworking.minecraft.api.v2.PacketDefinitionHelpers.identifier
import xyz.bluspring.modernnetworking.minecraft.api.packet.v2.MinecraftPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket

class CustomPayloadWrapper<T : NetworkPacket>(val registry: MinecraftPacketRegistry, val packet: T) : CustomPacketPayload {
    override fun write(buf: FriendlyByteBuf) {
        this.packet.definition.codec.cast<FriendlyByteBuf, T>().encode(buf, this.packet)
    }

    override fun id(): ResourceLocation = this.packet.definition.identifier
}
*///? }
