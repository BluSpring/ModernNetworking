package xyz.bluspring.modernnetworking.neoforge.packet

import io.netty.buffer.ByteBuf
import net.minecraft.network.ConnectionProtocol
import net.minecraft.network.protocol.PacketFlow
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl
import net.neoforged.fml.ModList
import net.neoforged.neoforge.common.extensions.ICommonPacketListener
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent
import net.neoforged.neoforge.network.handling.IPayloadContext
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.AbstractPacketHandlerRegistry
import xyz.bluspring.modernnetworking.api.v2.packet.registry.handler.PacketHandlerRegistry
import xyz.bluspring.modernnetworking.minecraft.CustomPayloadWrapper
import xyz.bluspring.modernnetworking.minecraft.MinecraftPacketHandlerRegistry
import java.util.Collections

abstract class NeoForgePacketHandlerRegistry<C, R>(packetRegistry: MinecraftPacketRegistry, val flow: PacketFlow, val protocol: ConnectionProtocol) : MinecraftPacketHandlerRegistry<C, R>(packetRegistry) {
    private val knownNamespaces: MutableSet<String> = Collections.synchronizedSet(mutableSetOf())
    private val handlers = mutableMapOf<PacketDefinition<*, *>, PacketHandlerRegistry.PacketHandler<*, C>>()

    abstract fun createPayloadContext(context: IPayloadContext): C

    protected fun tryRegisterNamespaceEvent(namespace: String) {
        if (knownNamespaces.add(namespace)) {
            val containerOpt = ModList.get().getModContainerById(namespace)
            if (containerOpt.isEmpty) {
                throw IllegalArgumentException("Could not find mod container by packet definition namespace $namespace! Unfortunately, NeoForge requires us to use the mod IDs.")
            }

            val container = containerOpt.orElseThrow()
            val eventBus = container.eventBus
                ?: throw IllegalArgumentException("Packet definition namespace under NeoForge mod ID $namespace does not support event buses!")

            eventBus.addListener<RegisterPayloadHandlersEvent> { event ->
                val registrar = event.registrar("1")
                val types = synchronized(this.packetRegistry.definitions) {
                    this.packetRegistry.definitions.filter { it.namespace == namespace }
                }.map {
                    this.packetRegistry.getOrCreateType(it)
                }

                for (typeAndCodec in types) {
                    when (flow) {
                        PacketFlow.SERVERBOUND -> {
                            when (protocol) {
                                ConnectionProtocol.PLAY -> {
                                    registrar.playToServer(typeAndCodec.type, typeAndCodec.codec) { packet, ctx ->
                                        val handler = this.handlers[packet.packet.definition] as? PacketHandlerRegistry.PacketHandler<NetworkPacket, C>
                                        handler?.handle(packet.packet, this.createPayloadContext(ctx))
                                    }
                                }

                                ConnectionProtocol.CONFIGURATION -> {
                                    registrar.configurationToServer(typeAndCodec.type, typeAndCodec.codec) { packet, ctx ->
                                        val handler = this.handlers[packet.packet.definition] as? PacketHandlerRegistry.PacketHandler<NetworkPacket, C>
                                        handler?.handle(packet.packet, this.createPayloadContext(ctx))
                                    }
                                }

                                else -> throw IllegalArgumentException("Unsupported protocol $flow/$protocol!")
                            }
                        }

                        PacketFlow.CLIENTBOUND -> {
                            when (protocol) {
                                ConnectionProtocol.PLAY -> {
                                    registrar.playToClient(typeAndCodec.type, typeAndCodec.codec) { packet, ctx ->
                                        val handler = this.handlers[packet.packet.definition] as? PacketHandlerRegistry.PacketHandler<NetworkPacket, C>
                                        handler?.handle(packet.packet, this.createPayloadContext(ctx))
                                    }
                                }

                                ConnectionProtocol.CONFIGURATION -> {
                                    registrar.configurationToClient(typeAndCodec.type, typeAndCodec.codec) { packet, ctx ->
                                        val handler = this.handlers[packet.packet.definition] as? PacketHandlerRegistry.PacketHandler<NetworkPacket, C>
                                        handler?.handle(packet.packet, this.createPayloadContext(ctx))
                                    }
                                }

                                else -> throw IllegalArgumentException("Unsupported protocol $flow/$protocol!")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun <B : ByteBuf, T : NetworkPacket> register(definition: PacketDefinition<B, T>, handler: PacketHandlerRegistry.PacketHandler<T, C>) {
        if (this.handlers.contains(definition))
            throw IllegalArgumentException("Packet handler for ${definition.namespace}:${definition.id} already exists!")

        this.handlers[definition] = handler
        this.tryRegisterNamespaceEvent(definition.namespace)
    }
}
