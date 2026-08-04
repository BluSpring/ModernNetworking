package xyz.bluspring.modernnetworking.neoforge.packet

import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistry

//? if >= 1.20.5 {
import io.netty.buffer.ByteBuf
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition

class NeoForgePacketRegistry : MinecraftPacketRegistry() {
    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>): PacketDefinition<B, T> {
        this.getOrCreateType(definition)
        return definition
    }
}
//? } else {
/*typealias NeoForgePacketRegistry = MinecraftPacketRegistry
*///? }
