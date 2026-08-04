package xyz.bluspring.modernnetworking.neoforge.packet

import net.minecraft.network.ConnectionProtocol
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.PacketFlow
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket
import net.minecraft.server.network.ConfigurationTask
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl
import net.neoforged.neoforge.network.event.RegisterConfigurationTasksEvent
import net.neoforged.neoforge.network.handling.IPayloadContext
import net.neoforged.neoforge.server.ServerLifecycleHooks
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftConfigurationPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ConfigurationContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerConfigurationPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper
import java.util.function.Consumer

class NeoForgeServerConfigurationPacketHandlerRegistry : NeoForgePacketHandlerRegistry<ServerConfigurationPacketContext, ServerConfigurationPacketListenerImpl>(
    MinecraftPacketRegistries.SERVER_CONFIGURATION, PacketFlow.SERVERBOUND, ConnectionProtocol.CONFIGURATION
), MinecraftConfigurationPacketHandlerRegistry<ServerConfigurationPacketContext, ServerConfigurationPacketListenerImpl> {
    private val definitions = mutableListOf<ConfigTaskDefinition>()
    private val handlers = mutableMapOf<ConfigTaskDefinition, MinecraftConfigurationPacketHandlerRegistry.ConfigurationTaskHandler<ServerConfigurationPacketListenerImpl>>()

    private data class ConfigTaskDefinition(val namespace: String, val id: String, val taskId: String) {
        val fullId: String
            get() = "$namespace:$id/$taskId"
    }

    fun handleRegisterEvent(event: RegisterConfigurationTasksEvent) {
        for (definition in this.definitions) {
            val handler = this.handlers[definition]!!

            val taskType = ConfigurationTask.Type(definition.fullId)
            val context = ConfigurationContext(definition.namespace, definition.id, definition.taskId, event.listener as ServerConfigurationPacketListenerImpl) {
                event.listener.finishCurrentTask(taskType)
            }

            val task = object : ConfigurationTask {
                override fun start(task: Consumer<Packet<*>>) {
                    handler.handleTask(context)
                    context.startConfigurationTask { packet ->
                        when (packet) {
                            is Packet<*> -> task.accept(packet)
                            is NetworkPacket -> task.accept(
                                ClientboundCustomPayloadPacket(
                                    CustomPayloadWrapper(
                                        MinecraftPacketRegistries.CLIENT_CONFIGURATION,
                                        packet
                                    )
                                )
                            )

                            else -> throw IllegalArgumentException("Unknown packet $packet!")
                        }
                    }
                }

                override fun type(): ConfigurationTask.Type = taskType
            }

            event.register(task)
        }
    }

    override fun createPayloadContext(context: IPayloadContext): ServerConfigurationPacketContext {
        // this is so hacky oml
        val listener = context.listener() as ServerConfigurationPacketListenerImpl
        return ServerConfigurationPacketContext(listener, ServerLifecycleHooks.getCurrentServer()!!)
    }

    override fun registerTask(namespace: String, id: String, taskId: String, handler: MinecraftConfigurationPacketHandlerRegistry.ConfigurationTaskHandler<ServerConfigurationPacketListenerImpl>) {
        val definition = ConfigTaskDefinition(namespace, id, taskId)
        if (this.handlers.contains(definition))
            throw IllegalArgumentException("A configuration task already exists under ID ${definition.fullId}!")

        this.definitions.add(definition)
        this.handlers[definition] = handler
    }

    override fun <T : NetworkPacket> send(receiver: ServerConfigurationPacketListenerImpl, packet: T) {
        receiver.send(CustomPayloadWrapper(this.packetRegistry, packet))
    }
}
