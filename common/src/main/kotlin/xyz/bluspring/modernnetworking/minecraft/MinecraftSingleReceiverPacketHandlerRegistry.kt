package xyz.bluspring.modernnetworking.minecraft

import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.MinecraftPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.SingleReceiverPacketHandlerRegistry

abstract class MinecraftSingleReceiverPacketHandlerRegistry<C>(opposingPacketRegistry: MinecraftPacketRegistry) : MinecraftPacketHandlerRegistry<C, Unit>(opposingPacketRegistry), SingleReceiverPacketHandlerRegistry<C> {
}
