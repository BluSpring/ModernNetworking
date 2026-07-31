package xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context

import net.minecraft.client.Minecraft

abstract class ClientCommonPacketContext<T>(
    val handler: T,
    val client: Minecraft,
)
