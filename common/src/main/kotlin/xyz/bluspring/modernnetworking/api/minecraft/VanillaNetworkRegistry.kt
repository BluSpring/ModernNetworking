package xyz.bluspring.modernnetworking.api.minecraft

//? if >= 1.20.2 {
import io.netty.buffer.ByteBuf
//? if >= 1.20.6 {
/*import net.minecraft.network.codec.StreamCodec
*///?}
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import xyz.bluspring.modernnetworking.ModernNetworking
import xyz.bluspring.modernnetworking.modern.CustomPayloadWrapper
import xyz.bluspring.modernnetworking.api.NetworkPacket
import xyz.bluspring.modernnetworking.api.PacketDefinition
//?}
import org.jetbrains.annotations.ApiStatus
import xyz.bluspring.modernnetworking.api.AbstractNetworkRegistry

class VanillaNetworkRegistry private constructor(namespace: String) : AbstractNetworkRegistry<VanillaClientContext, VanillaServerContext>(namespace) {
    //? if >= 1.20.6 {
    /*@ApiStatus.Internal val clientTypes = mutableMapOf<String, CustomPacketPayload.Type<*>>()
    @ApiStatus.Internal val clientCodecs = mutableMapOf<String, StreamCodec<*, CustomPayloadWrapper<*>>>()

    @ApiStatus.Internal val serverTypes = mutableMapOf<String, CustomPacketPayload.Type<*>>()
    @ApiStatus.Internal val serverCodecs = mutableMapOf<String, StreamCodec<*, CustomPayloadWrapper<*>>>()

    override fun <T : NetworkPacket, B : ByteBuf> registerClientbound(
        existing: PacketDefinition<T, B>
    ): PacketDefinition<T, B> {
        val definition = super.registerClientbound(existing)
        val namespace = definition.namespace
        val id = definition.id

        val type = CustomPacketPayload.Type<CustomPayloadWrapper<T>>(ModernNetworking.id(namespace, id))
        clientTypes[id] = type
        clientCodecs[id] = StreamCodec.of<B, CustomPayloadWrapper<T>>({ buf, data ->
            definition.codec.encode(buf, data.packet)
        }, { buf ->
            CustomPayloadWrapper(type, definition.codec.decode(buf))
        }) as StreamCodec<*, CustomPayloadWrapper<*>>

        return definition
    }

    override fun <T : NetworkPacket, B : ByteBuf> registerServerbound(
        existing: PacketDefinition<T, B>
    ): PacketDefinition<T, B> {
        val definition = super.registerServerbound(existing)
        val namespace = definition.namespace
        val id = definition.id

        val type = CustomPacketPayload.Type<CustomPayloadWrapper<T>>(ModernNetworking.id(namespace, id))
        serverTypes[id] = type
        serverCodecs[id] = StreamCodec.of<B, CustomPayloadWrapper<T>>({ buf, data ->
            definition.codec.encode(buf, data.packet)
        }, { buf ->
            CustomPayloadWrapper(type, definition.codec.decode(buf))
        }) as StreamCodec<*, CustomPayloadWrapper<*>>

        return definition
    }
    *///?}

    companion object {
        @ApiStatus.Internal
        val registries = mutableMapOf<String, VanillaNetworkRegistry>()

        /**
         * Creates a network registry for the given namespace.
         */
        @JvmStatic
        fun create(namespace: String): VanillaNetworkRegistry {
            if (registries.contains(namespace))
                throw IllegalArgumentException("Registry under namespace $namespace already exists!")

            val registry = VanillaNetworkRegistry(namespace)
            registries[namespace] = registry

            return registry
        }

        @JvmStatic
        fun get(namespace: String): VanillaNetworkRegistry? {
            return registries[namespace]
        }
    }
}