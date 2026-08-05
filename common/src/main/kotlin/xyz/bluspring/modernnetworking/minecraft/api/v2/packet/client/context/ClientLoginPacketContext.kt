package xyz.bluspring.modernnetworking.minecraft.api.v2.packet.client.context

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl

class ClientLoginPacketContext(
    handler: ClientHandshakePacketListenerImpl,
    client: Minecraft,
) : ClientCommonPacketContext<ClientHandshakePacketListenerImpl>(handler, client)
