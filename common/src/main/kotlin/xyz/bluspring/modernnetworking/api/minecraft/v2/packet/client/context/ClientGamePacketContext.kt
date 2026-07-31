package xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.client.player.LocalPlayer

class ClientGamePacketContext(
    val player: LocalPlayer,
    client: Minecraft,
) : ClientCommonPacketContext<ClientPacketListener>(player.connection, client)
