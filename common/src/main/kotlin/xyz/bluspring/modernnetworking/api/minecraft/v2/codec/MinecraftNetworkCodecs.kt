package xyz.bluspring.modernnetworking.api.minecraft.v2.codec

import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
//? if >= 1.20.5 {
import net.minecraft.network.chat.ComponentSerialization
import net.minecraft.network.codec.StreamCodec
import io.netty.buffer.ByteBuf
//? } else {
/*import net.minecraft.network.chat.Component
*///? }
import net.minecraft.world.item.ItemStack
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec

object MinecraftNetworkCodecs {
    @JvmField
    val ITEM_STACK = //? if >= 1.20.6 {
        ItemStack.STREAM_CODEC.toNetworkCodec()
    //?} else {
        /*NetworkCodec<FriendlyByteBuf, ItemStack>(FriendlyByteBuf::writeItem, FriendlyByteBuf::readItem)
    *///?}

    @JvmField val COMPOUND_TAG: NetworkCodec<FriendlyByteBuf, CompoundTag> = NetworkCodec({ buf, value -> buf.writeNbt(value) }) { buf -> buf.readNbt()!! }

    @JvmField
    //? if >= 1.20.6 {
    val COMPONENT = ComponentSerialization.STREAM_CODEC.toNetworkCodec()
    //?} else {
    /*val COMPONENT = NetworkCodec<FriendlyByteBuf, Component>(FriendlyByteBuf::writeComponent, FriendlyByteBuf::readComponent)
    *///?}

    //? if >= 1.20.5 {
    @JvmStatic
    fun <B : ByteBuf, V : Any> StreamCodec<B, V>.toNetworkCodec(): NetworkCodec<B, V> {
        return NetworkCodec(this::encode, this::decode)
    }
    //? }
}
