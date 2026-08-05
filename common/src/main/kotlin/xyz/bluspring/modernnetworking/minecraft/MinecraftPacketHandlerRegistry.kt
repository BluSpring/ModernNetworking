package xyz.bluspring.modernnetworking.minecraft

import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.MinecraftPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry

abstract class MinecraftPacketHandlerRegistry<C, R>(val opposingPacketRegistry: MinecraftPacketRegistry) : PacketHandlerRegistry<C, R> {
}
