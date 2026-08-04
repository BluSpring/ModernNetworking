package xyz.bluspring.modernnetworking.api.minecraft.v2.packet

import xyz.bluspring.modernnetworking.api.v2.packet.registry.PacketRegistry
import xyz.bluspring.modernnetworking.minecraft.PlatformProxy

object MinecraftPacketRegistries {
    /**
     * The serverbound [PacketRegistry] for the login phase.
     */
    @JvmField val SERVER_LOGIN = PlatformProxy.instance.createServerLoginRegistry()

    //? if >= 1.20.2 {
    /*/**
     * The serverbound [PacketRegistry] for the configuration phase.
     */
    @JvmField val SERVER_CONFIGURATION = PlatformProxy.instance.createServerConfigRegistry()
    *///? }

    /**
     * The serverbound [PacketRegistry] for the play (also known as the game) phase.
     */
    @JvmField val SERVER_PLAY = PlatformProxy.instance.createServerGameRegistry()

    /**
     * The clientbound [PacketRegistry] for the login phase.
     */
    @JvmField val CLIENT_LOGIN = PlatformProxy.instance.createClientLoginRegistry()

    //? if >= 1.20.2 {
    /*/**
     * The clientbound [PacketRegistry] for the configuration phase.
     */
    @JvmField val CLIENT_CONFIGURATION = PlatformProxy.instance.createClientConfigRegistry()
    *///? }

    /**
     * The clientbound [PacketRegistry] for the play (also known as the game) phase.
     */
    @JvmField val CLIENT_PLAY = PlatformProxy.instance.createClientGameRegistry()
}
