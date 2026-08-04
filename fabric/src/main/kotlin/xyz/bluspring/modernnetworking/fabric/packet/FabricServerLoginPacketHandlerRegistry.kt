package xyz.bluspring.modernnetworking.fabric.packet

import io.netty.buffer.ByteBuf
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking
import net.minecraft.server.network.ServerLoginPacketListenerImpl
import xyz.bluspring.modernnetworking.api.minecraft.v2.PacketDefinitionHelpers.identifier
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.ServerLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerLoginPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.fabric.api.v2.FabricPacketSender.sendPacket

class FabricServerLoginPacketHandlerRegistry : ServerLoginPacketHandlerRegistry<ServerLoginPacketContext, ServerLoginPacketListenerImpl>() {
    override fun <B : ByteBuf, T : NetworkPacket> registerLogin(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ServerLoginPacketContext>, unknownHandler: UnknownPacketHandler<ServerLoginPacketContext>) {
        ServerLoginNetworking.registerGlobalReceiver(definition.identifier) { server, listener, understood, buf, synchronizer, sender ->
            val context = ServerLoginPacketContext(listener, server, sender::sendPacket,
                { sender.sendPacket(it) }, understood, synchronizer::waitFor)

            if (understood) {
                val packet = definition.codec.decode(buf as B)
                handler.handle(packet, context)
            } else {
                unknownHandler.handle(context)
            }
        }
    }
}
