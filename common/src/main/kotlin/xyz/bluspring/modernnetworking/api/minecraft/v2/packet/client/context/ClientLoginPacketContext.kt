package xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientConfigurationPacketListenerImpl
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.network.protocol.login.ClientLoginPacketListener

class ClientLoginPacketContext(
    handler: ClientHandshakePacketListenerImpl,
    client: Minecraft,
) : ClientCommonPacketContext<ClientHandshakePacketListenerImpl>(handler, client)
