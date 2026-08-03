package xyz.bluspring.modernnetworking.minecraft

import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.SingleReceiverPacketHandlerRegistry

abstract class MinecraftSingleReceiverPacketHandlerRegistry<C>(packetRegistry: MinecraftPacketRegistry) : MinecraftPacketHandlerRegistry<C, Unit>(packetRegistry), SingleReceiverPacketHandlerRegistry<C> {
}
