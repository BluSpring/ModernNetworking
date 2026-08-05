package xyz.bluspring.modernnetworking.minecraft.api.v2.packet

//? if >= 1.20.5 {
/*import io.netty.buffer.ByteBuf
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
//? if >= 1.21.11 {
/*import net.minecraft.resources.Identifier as ResourceLocation
*///? } else {
import net.minecraft.resources.ResourceLocation
//? }
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.PacketRegistry
import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper

abstract class MinecraftPacketRegistry : PacketRegistry {
    protected val definitionsToTypes: MutableMap<PacketDefinition<*, *>, CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, CustomPayloadWrapper<*>>> = mutableMapOf()

    val definitions: Collection<PacketDefinition<*, *>>
        get() = this.definitionsToTypes.keys

    fun <B : ByteBuf, T : NetworkPacket> getOrCreateType(definition: PacketDefinition<B, T>): CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, CustomPayloadWrapper<T>> {
        if (this.definitionsToTypes.contains(definition)) {
            return this.definitionsToTypes[definition]!! as CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, CustomPayloadWrapper<T>>
        }

        val type = CustomPacketPayload.Type<CustomPayloadWrapper<T>>(
            ResourceLocation
                //? if >= 1.21 {
                /*.fromNamespaceAndPath
                *///? }
                (definition.namespace, definition.id)
        )
        val codec: StreamCodec<B, CustomPayloadWrapper<T>> = StreamCodec.of({ buf, value ->
            definition.codec.encode(buf, value.packet)
        }, { buf ->
            CustomPayloadWrapper(this, definition.codec.decode(buf))
        })
        val typeAndCodec: CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, CustomPayloadWrapper<T>> = CustomPacketPayload.TypeAndCodec(type, codec as StreamCodec<FriendlyByteBuf, CustomPayloadWrapper<T>>)

        // yep, that works
        (this.definitionsToTypes as MutableMap<PacketDefinition<*, *>, CustomPacketPayload.TypeAndCodec<FriendlyByteBuf, CustomPayloadWrapper<T>>>)[definition] = typeAndCodec

        return typeAndCodec
    }
}
*///? } else {
import xyz.bluspring.modernnetworking.api.v2.packet.registry.DefaultedPacketRegistry

class MinecraftPacketRegistry : DefaultedPacketRegistry()
//? }
