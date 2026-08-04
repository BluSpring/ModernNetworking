package xyz.bluspring.modernnetworking.neoforge.packet.client

import io.netty.buffer.ByteBuf
//? if >= 1.20.5 {
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl
import net.minecraft.network.ConnectionProtocol
import net.minecraft.network.protocol.PacketFlow
import net.minecraft.server.network.ServerLoginPacketListenerImpl
import net.neoforged.neoforge.network.handling.MainThreadPayloadHandler
import net.neoforged.neoforge.network.registration.NetworkRegistry
import net.neoforged.neoforge.server.ServerLifecycleHooks
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.ServerLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerLoginPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import java.util.Optional
//? }
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.ClientLoginPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientLoginPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition

class NeoForgeClientLoginPacketHandlerRegistry : ClientLoginPacketHandlerRegistry<ClientLoginPacketContext>() {
    //? if >= 1.20.5 {
    private val internalTypeRegistry = object : MinecraftPacketRegistry() {
        override fun <B : ByteBuf, T : NetworkPacket> register(
            definition: PacketDefinition<B, T>
        ): PacketDefinition<B, T> {
            this.getOrCreateType(definition)
            return definition
        }
    }
    //? }

    override fun <B : ByteBuf, T : NetworkPacket> registerLogin(
        definition: PacketDefinition<B, T>,
        handler: LoginPacketHandler<T, ClientLoginPacketContext>
    ) {
        //? if >= 1.20.5 {
        // NeoForge doesn't have a proper API to do any of this, for some reason.
        // We're using internal APIs to do this because of that.
        val typeAndCodec = this.internalTypeRegistry.getOrCreateType(definition)
        NetworkRegistry.register(typeAndCodec.type, typeAndCodec.codec, MainThreadPayloadHandler { packet, ctx ->
            handler.handle(packet.packet, ClientLoginPacketContext(ctx.listener() as ClientHandshakePacketListenerImpl, Minecraft.getInstance()))
        }, listOf(ConnectionProtocol.LOGIN), Optional.of(PacketFlow.SERVERBOUND), "1", true)
        //? } else {
        /*// We don't have an API for login on 1.20.2-1.20.4! what?
        TODO("Not yet implemented. Blame NeoForge.")
        *///? }
    }
}
