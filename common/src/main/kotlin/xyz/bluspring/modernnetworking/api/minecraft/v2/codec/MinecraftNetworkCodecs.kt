package xyz.bluspring.modernnetworking.api.minecraft.v2.codec

import io.netty.buffer.ByteBuf
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.ComponentSerialization
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec

object MinecraftNetworkCodecs {
    @JvmField
    val ITEM_STACK = //? if >= 1.20.6 {
        /*ItemStack.STREAM_CODEC.toNetworkCodec()
    *///?} else {
        NetworkCodec<ItemStack, FriendlyByteBuf>(FriendlyByteBuf::writeItem, FriendlyByteBuf::readItem)
    //?}

    @JvmField val COMPOUND_TAG: NetworkCodec<FriendlyByteBuf, CompoundTag> = NetworkCodec({ buf, value -> buf.writeNbt(value) }) { buf -> buf.readNbt()!! }

    @JvmField
    //? if >= 1.20.6 {
    /*val COMPONENT = ComponentSerialization.STREAM_CODEC.toNetworkCodec()
    *///?} else {
    val COMPONENT = NetworkCodec<Component, FriendlyByteBuf>(FriendlyByteBuf::writeComponent, FriendlyByteBuf::readComponent)
    //?}

    //? if >= 1.20.5 {
    /*@JvmStatic
    fun <B : ByteBuf, V : Any> StreamCodec<B, V>.toNetworkCodec(): NetworkCodec<B, V> {
        return NetworkCodec(this::encode, this::decode)
    }
    *///? }
}
