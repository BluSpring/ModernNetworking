package xyz.bluspring.modernnetworking.api.minecraft

import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.NetworkPacket
import xyz.bluspring.modernnetworking.api.PacketDefinition
import org.jetbrains.annotations.ApiStatus
import xyz.bluspring.modernnetworking.api.AbstractNetworkRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftServerPacketHandlers
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.MinecraftClientPacketHandlers

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Refactored to provide access to more phases i.e. login and configuration phases.")
class VanillaNetworkRegistry private constructor(namespace: String) : AbstractNetworkRegistry<VanillaClientContext, VanillaServerContext>(namespace) {
    override fun <T : NetworkPacket, B : ByteBuf> registerClientbound(
        existing: PacketDefinition<T, B>
    ): PacketDefinition<T, B> {
        val definition = super.registerClientbound(existing)
        MinecraftPacketRegistries.CLIENT_PLAY.register(definition.asV2)

        return definition
    }

    override fun <T : NetworkPacket, B : ByteBuf> registerServerbound(
        existing: PacketDefinition<T, B>
    ): PacketDefinition<T, B> {
        val definition = super.registerServerbound(existing)
        MinecraftPacketRegistries.SERVER_PLAY.register(definition.asV2)

        return definition
    }

    override fun <T : NetworkPacket, B : ByteBuf> addClientboundHandler(definition: PacketDefinition<T, B>, handler: NetworkHandler<VanillaClientContext, T>) {
        super.addClientboundHandler(definition, handler)
        MinecraftClientPacketHandlers.PLAY.register(definition.asV2) { packet, ctx ->
            handler.handle(packet.original, VanillaClientContext(ctx.client, ctx.player))
        }
    }

    override fun <T : NetworkPacket, B : ByteBuf> addServerboundHandler(definition: PacketDefinition<T, B>, handler: NetworkHandler<VanillaServerContext, T>) {
        super.addServerboundHandler(definition, handler)
        MinecraftServerPacketHandlers.PLAY.register(definition.asV2) { packet, ctx ->
            handler.handle(packet.original, VanillaServerContext(ctx.server, ctx.player))
        }
    }

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
