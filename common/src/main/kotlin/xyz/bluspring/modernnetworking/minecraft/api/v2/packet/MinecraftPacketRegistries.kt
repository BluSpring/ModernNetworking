package xyz.bluspring.modernnetworking.minecraft.api.v2.packet

import xyz.bluspring.modernnetworking.api.v2.packet.registry.PacketRegistry
import xyz.bluspring.modernnetworking.minecraft.PlatformProxy

object MinecraftPacketRegistries {
    /**
     * The [PacketRegistry] for the login phase.
     */
    @JvmField val LOGIN = PlatformProxy.instance.createLoginRegistry()

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
