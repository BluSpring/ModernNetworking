package xyz.bluspring.modernnetworking.neoforge.packet

import io.netty.buffer.ByteBuf
import net.minecraft.server.network.ServerLoginPacketListenerImpl
//? if >= 1.20.5 {
/*import net.minecraft.network.ConnectionProtocol
import net.minecraft.network.protocol.PacketFlow
import net.neoforged.neoforge.network.handling.MainThreadPayloadHandler
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistry
import net.neoforged.neoforge.network.registration.NetworkRegistry
import net.neoforged.neoforge.server.ServerLifecycleHooks
import java.util.Optional
*///? }
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.ServerLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerLoginPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry

class NeoForgeServerLoginPacketHandlerRegistry : ServerLoginPacketHandlerRegistry<ServerLoginPacketContext, ServerLoginPacketListenerImpl>() {
    //? if >= 1.20.5 {
    /*private val internalTypeRegistry = object : MinecraftPacketRegistry() {
        override fun <B : ByteBuf, T : NetworkPacket> register(
            definition: PacketDefinition<B, T>
        ): PacketDefinition<B, T> {
            this.getOrCreateType(definition)
            return definition
        }
    }
    *///? }

    override fun <B : ByteBuf, T : NetworkPacket> registerLogin(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ServerLoginPacketContext>, unknownHandler: UnknownPacketHandler<ServerLoginPacketContext>) {
        //? if >= 1.20.5 {
        /*// NeoForge doesn't have a proper API to do any of this, for some reason.
        // We're using internal APIs to do this because of that.
        val typeAndCodec = this.internalTypeRegistry.getOrCreateType(definition)
        NetworkRegistry.register(typeAndCodec.type, typeAndCodec.codec, MainThreadPayloadHandler { packet, ctx ->
            handler.handle(packet.packet, ServerLoginPacketContext(ctx.listener() as ServerLoginPacketListenerImpl,
                ServerLifecycleHooks.getCurrentServer()!!, true)
            { future ->
                TODO("Not yet implemented. Blame NeoForge.")
            })
        }, listOf(ConnectionProtocol.LOGIN), Optional.of(PacketFlow.SERVERBOUND), "1", true)
        *///? } else {
        // We don't have an API for login on 1.20.2-1.20.4! what?
        TODO("Not yet implemented. Blame NeoForge.")
        //? }
    }
}
