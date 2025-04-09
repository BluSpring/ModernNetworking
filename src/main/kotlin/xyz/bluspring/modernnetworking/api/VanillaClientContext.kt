package xyz.bluspring.modernnetworking.api

import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer

data class VanillaClientContext(
    val client: Minecraft,
    val player: LocalPlayer
)
