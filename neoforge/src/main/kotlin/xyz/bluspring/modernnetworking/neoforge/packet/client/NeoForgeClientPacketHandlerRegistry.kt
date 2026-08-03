package xyz.bluspring.modernnetworking.neoforge.packet.client

import net.minecraft.client.Minecraft
import net.minecraft.network.ConnectionProtocol
import net.minecraft.network.protocol.PacketFlow
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.SingleReceiverPacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper
import xyz.bluspring.modernnetworking.neoforge.packet.NeoForgePacketHandlerRegistry

abstract class NeoForgeClientPacketHandlerRegistry<C>(packetRegistry: MinecraftPacketRegistry, flow: PacketFlow, protocol: ConnectionProtocol) : NeoForgePacketHandlerRegistry<C, Unit>(packetRegistry, flow, protocol), SingleReceiverPacketHandlerRegistry<C> {
    override fun <T : NetworkPacket> send(packet: T) {
        Minecraft.getInstance().connection!!.send(CustomPayloadWrapper(this.packetRegistry, packet))
    }
}
