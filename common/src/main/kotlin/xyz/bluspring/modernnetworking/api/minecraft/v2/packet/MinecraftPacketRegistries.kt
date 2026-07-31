package xyz.bluspring.modernnetworking.api.minecraft.v2.packet

import xyz.bluspring.modernnetworking.api.v2.packet.PacketRegistry
import xyz.bluspring.modernnetworking.minecraft.PlatformProxy

object MinecraftPacketRegistries {
    @JvmField val SERVER_LOGIN = PlatformProxy.instance.createServerLoginRegistry()
    @JvmField val SERVER_CONFIGURATION = PlatformProxy.instance.createServerConfigRegistry()
    @JvmField val SERVER_PLAY = PlatformProxy.instance.createServerGameRegistry()

    @JvmField val CLIENT_LOGIN = PlatformProxy.instance.createClientLoginRegistry()
    @JvmField val CLIENT_CONFIGURATION = PlatformProxy.instance.createClientConfigRegistry()
    @JvmField val CLIENT_PLAY = PlatformProxy.instance.createClientGameRegistry()
}
