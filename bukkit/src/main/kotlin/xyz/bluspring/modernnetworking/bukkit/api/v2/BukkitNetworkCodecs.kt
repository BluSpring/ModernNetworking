package xyz.bluspring.modernnetworking.bukkit.api.v2

import io.netty.buffer.ByteBuf
import org.bukkit.inventory.ItemStack
import xyz.bluspring.modernnetworking.api.v2.codec.NetworkCodec
import xyz.bluspring.modernnetworking.internal.readByteArray
import xyz.bluspring.modernnetworking.internal.writeByteArray

/**
 * A collection of some useful network codecs for Bukkit. Note that these most likely
 * do NOT map 1:1 with the modded Minecraft network codecs!
 */
object BukkitNetworkCodecs {
    @JvmField
    val ITEM_STACK = NetworkCodec<ByteBuf, ItemStack>({ buf, stack ->
        val serialized = stack.serializeAsBytes()
        buf.writeByteArray(serialized)
    }, { buf ->
        ItemStack.deserializeBytes(buf.readByteArray())
    })
}
