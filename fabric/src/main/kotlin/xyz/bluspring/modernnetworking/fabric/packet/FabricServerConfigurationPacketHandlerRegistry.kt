package xyz.bluspring.modernnetworking.fabric.packet

//? if >= 1.20.2 {
/*import io.netty.buffer.ByteBuf
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket
import net.minecraft.server.network.ConfigurationTask
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl
//? if <= 1.20.4 {
import io.netty.buffer.Unpooled
import net.minecraft.network.FriendlyByteBuf
import xyz.bluspring.modernnetworking.api.minecraft.v2.PacketDefinitionHelpers.identifier
//? }
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.ConfigurationPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ConfigurationContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.context.ServerConfigurationPacketContext
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper
import xyz.bluspring.modernnetworking.minecraft.MinecraftPacketHandlerRegistry
import java.util.function.Consumer

class FabricServerConfigurationPacketHandlerRegistry : MinecraftPacketHandlerRegistry<ServerConfigurationPacketContext, ServerConfigurationPacketListenerImpl>(MinecraftPacketRegistries.CLIENT_CONFIGURATION), ConfigurationPacketHandlerRegistry<ServerConfigurationPacketContext, ServerConfigurationPacketListenerImpl> {
    private val definitions = mutableListOf<ConfigTaskDefinition>()
    private val handlers = mutableMapOf<ConfigTaskDefinition, ConfigurationPacketHandlerRegistry.ConfigurationTaskHandler<ServerConfigurationPacketListenerImpl>>()

    private data class ConfigTaskDefinition(val namespace: String, val id: String, val taskId: String) {
        val fullId: String
            get() = "$namespace:$id/$taskId"
    }

    init {
        ServerConfigurationConnectionEvents.CONFIGURE.register { listener, _ ->
            for (definition in this.definitions) {
                val handler = this.handlers[definition]!!

                val taskType = ConfigurationTask.Type(definition.fullId)
                val context = ConfigurationContext(definition.namespace, definition.id, definition.taskId, listener) {
                    listener.completeTask(taskType)
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
                                            this@FabricServerConfigurationPacketHandlerRegistry.opposingPacketRegistry,
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

                listener.addTask(task)
            }
        }
    }

    override fun registerTask(namespace: String, id: String, taskId: String, handler: ConfigurationPacketHandlerRegistry.ConfigurationTaskHandler<ServerConfigurationPacketListenerImpl>) {
        val definition = ConfigTaskDefinition(namespace, id, taskId)
        if (this.handlers.contains(definition))
            throw IllegalArgumentException("A configuration task already exists under ID ${definition.fullId}!")

        this.definitions.add(definition)
        this.handlers[definition] = handler
    }

    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, ServerConfigurationPacketContext>) {
        //? if >= 1.20.5 {
        /*ServerConfigurationNetworking.registerGlobalReceiver(this.opposingPacketRegistry.getOrCreateType(definition).type) { packet, ctx ->
            handler.handle(packet.packet, ServerConfigurationPacketContext(
                //? if >= 26.1 {
                /*ctx.packetListener()
                *///? } else {
                ctx.networkHandler()
                //? }
                , ctx.server())
            )
        }
        *///? } else {
        ServerConfigurationNetworking.registerGlobalReceiver(definition.identifier) { server, impl, buf, sender ->
            val packet = definition.codec.cast<FriendlyByteBuf, T>().decode(buf)
            handler.handle(packet, ServerConfigurationPacketContext(impl, server))
        }
        //? }
    }

    override fun <T : NetworkPacket> send(receiver: ServerConfigurationPacketListenerImpl, packet: T) {
        //? if >= 1.20.5 {
        /*ServerConfigurationNetworking.send(receiver, CustomPayloadWrapper(this.opposingPacketRegistry, packet))
        *///? } else {
        val buf = FriendlyByteBuf(Unpooled.buffer())
        packet.definition.codec.cast<FriendlyByteBuf, T>().encode(buf, packet)
        ServerConfigurationNetworking.send(receiver, packet.definition.identifier, buf)
        //? }
    }
}
*///? }
