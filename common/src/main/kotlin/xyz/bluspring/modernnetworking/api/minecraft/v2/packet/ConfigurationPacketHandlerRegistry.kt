package xyz.bluspring.modernnetworking.api.minecraft.v2.packet

import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ConfigurationContext
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry

interface ConfigurationPacketHandlerRegistry<C, R> : PacketHandlerRegistry<C, R> {
    /**
     * Registers a new configuration task under this packet handler. Must be registered in the order you want your tasks to run in.
     */
    fun registerTask(namespace: String, id: String, taskId: String, handler: ConfigurationTaskHandler<R>)

    fun interface ConfigurationTaskHandler<R> {
        fun handleTask(context: ConfigurationContext<R>)
    }
}
