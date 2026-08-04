package xyz.bluspring.modernnetworking.forge.packet

import io.netty.buffer.ByteBuf
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.event.EventNetworkChannel
import xyz.bluspring.modernnetworking.api.minecraft.v2.PacketDefinitionHelpers.identifier
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry

abstract class ForgePacketHandlerRegistry<C, R> : PacketHandlerRegistry<C, R> {
    final override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, C>) {
        val channel = NetworkRegistry.newEventChannel(definition.identifier, { "1" }, { true }, { true })
        this.setupEventChannel(channel, definition, handler)
    }

    protected abstract fun <B : ByteBuf, T : NetworkPacket> setupEventChannel(channel: EventNetworkChannel, definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, C>)
}
