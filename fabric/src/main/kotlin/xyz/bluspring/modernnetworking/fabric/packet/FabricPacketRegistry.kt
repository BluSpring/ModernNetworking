package xyz.bluspring.modernnetworking.fabric.packet

//? if >= 1.20.5 {
/*import io.netty.buffer.ByteBuf
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.network.FriendlyByteBuf
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition

class FabricPacketRegistry<B : FriendlyByteBuf>(val fabricRegistry: PayloadTypeRegistry<B>) : MinecraftPacketRegistry() {
    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>): PacketDefinition<B, T> {
        val typeAndCodec = this.getOrCreateType(definition)
        this.fabricRegistry.register(typeAndCodec.type, typeAndCodec.codec)
        return definition
    }
}
*///? }
