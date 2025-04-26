package xyz.bluspring.modernnetworking.api.minecraft

//? if >= 1.20.6 {
/*import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.chat.ComponentSerialization
*///?}
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import xyz.bluspring.modernnetworking.api.NetworkCodec

object VanillaCodecs {
    @JvmField
    val ITEM_STACK = //? if >= 1.20.6 {
        /*ItemStack.STREAM_CODEC.toNetworkCodec()
    *///?} else {
        NetworkCodec<ItemStack, FriendlyByteBuf>(FriendlyByteBuf::writeItem, FriendlyByteBuf::readItem)
    //?}

    @JvmField
    val COMPOUND_TAG = NetworkCodec<CompoundTag, FriendlyByteBuf>({ buf, value ->
        buf.writeNbt(value)
    }, { buf ->
        buf.readNbt() ?: CompoundTag()
    })

    @JvmField
    //? if >= 1.20.6 {
    /*val COMPONENT = streamCodecToNetworkCodec(ComponentSerialization.STREAM_CODEC)
    *///?} else {
    val COMPONENT = NetworkCodec<Component, FriendlyByteBuf>(FriendlyByteBuf::writeComponent, FriendlyByteBuf::readComponent)
    //?}

    //? if >= 1.20.6 {
    /*@JvmStatic
    fun <B : ByteBuf, V> streamCodecToNetworkCodec(codec: StreamCodec<B, V>): NetworkCodec<V, B> {
        return codec.toNetworkCodec()
    }

    fun <B : ByteBuf, V> StreamCodec<B, V>.toNetworkCodec(): NetworkCodec<V, B> {
        return NetworkCodec({ buf, value ->
            this.encode(buf, value)
        }, { buf ->
            this.decode(buf)
        })
    }
    *///?}
}