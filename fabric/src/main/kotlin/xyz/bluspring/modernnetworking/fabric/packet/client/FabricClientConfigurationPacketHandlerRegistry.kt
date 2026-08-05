package xyz.bluspring.modernnetworking.fabric.packet.client

//? if >= 1.20.2 {
/*import io.netty.buffer.ByteBuf
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking
//? if >= 1.20.5 && <= 1.20.6 {
/*import net.fabricmc.fabric.impl.networking.client.ClientConfigurationNetworkAddon
import net.minecraft.client.multiplayer.ClientConfigurationPacketListenerImpl
import net.minecraft.network.Connection
*///? }
//? if <= 1.20.4 {
import io.netty.buffer.Unpooled
import net.minecraft.network.FriendlyByteBuf
import xyz.bluspring.modernnetworking.minecraft.api.v2.PacketDefinitionHelpers.identifier
//? } else {
/*import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper
*///? }
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.client.context.ClientConfigurationPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.MinecraftSingleReceiverPacketHandlerRegistry

class FabricClientConfigurationPacketHandlerRegistry : MinecraftSingleReceiverPacketHandlerRegistry<ClientConfigurationPacketContext>(MinecraftPacketRegistries.SERVER_CONFIGURATION) {
    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ClientConfigurationPacketContext>) {
        //? if >= 1.20.5 {
        /*ClientConfigurationNetworking.registerGlobalReceiver(this.opposingPacketRegistry.getOrCreateType(definition).type) { packet, ctx ->
            val networkHandler =
                //? if >= 26.1 {
                /*ctx.packetListener()
                *///? } else if >= 1.21 {
                /*ctx.networkHandler()
                *///? } else {
                // stupid hack but we don't have access to networkHandler in 1.20.6
                (((ctx.responseSender() as? ClientConfigurationNetworkAddon)?.channelInfoHolder as? Connection)
                    ?.packetListener as? ClientConfigurationPacketListenerImpl)
                    ?: return@registerGlobalReceiver
                //? }
            handler.handle(packet.packet, ClientConfigurationPacketContext(networkHandler, ctx.client()))
        }
        *///? } else {
        ClientConfigurationNetworking.registerGlobalReceiver(definition.identifier) { client, impl, buf, sender ->
            val packet = definition.codec.cast<FriendlyByteBuf, T>().decode(buf)
            handler.handle(packet, ClientConfigurationPacketContext(impl, client))
        }
        //? }
    }

    override fun <T : NetworkPacket> send(packet: T) {
        //? if >= 1.20.5 {
        /*ClientConfigurationNetworking.send(CustomPayloadWrapper(this.opposingPacketRegistry, packet))
        *///? } else {
        val buf = FriendlyByteBuf(Unpooled.buffer())
        packet.definition.codec.cast<FriendlyByteBuf, T>().encode(buf, packet)
        ClientConfigurationNetworking.send(packet.definition.identifier, buf)
        //? }
    }
}
*///? }
