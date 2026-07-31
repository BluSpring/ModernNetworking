package xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.context

import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientConfigurationPacketListenerImpl
import net.minecraft.client.multiplayer.ClientPacketListener

class ClientConfigurationPacketContext(
    handler: ClientConfigurationPacketListenerImpl,
    client: Minecraft,
) : ClientCommonPacketContext<ClientConfigurationPacketListenerImpl>(handler, client)
