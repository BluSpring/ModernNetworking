package xyz.bluspring.modernnetworking.api.minecraft.v2.packet

import xyz.bluspring.modernnetworking.minecraft.PlatformProxy

object MinecraftPacketRegistries {
    @JvmField val SERVER_LOGIN = PlatformProxy.instance.createServerLoginRegistry()
    //? if >= 1.20.2 {
    /*@JvmField val SERVER_CONFIGURATION = PlatformProxy.instance.createServerConfigRegistry()
    *///? }
    @JvmField val SERVER_PLAY = PlatformProxy.instance.createServerGameRegistry()

    @JvmField val CLIENT_LOGIN = PlatformProxy.instance.createClientLoginRegistry()
    //? if >= 1.20.2 {
    /*@JvmField val CLIENT_CONFIGURATION = PlatformProxy.instance.createClientConfigRegistry()
    *///? }
    @JvmField val CLIENT_PLAY = PlatformProxy.instance.createClientGameRegistry()
}
