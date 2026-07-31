package xyz.bluspring.modernnetworking.api.minecraft

import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer

@Deprecated(level = DeprecationLevel.HIDDEN, message = "Refactored to provide further context information.", replaceWith = ReplaceWith("ClientCommonPacketContext", "xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context.ClientCommonPacketContext"))
data class VanillaClientContext(
    val client: Minecraft,
    val player: LocalPlayer,
)
