package xyz.bluspring.modernnetworking.api

import io.netty.buffer.ByteBuf

/**
 * The base network registry. You may be looking for VanillaNetworkRegistry.
 */
abstract class AbstractNetworkRegistry<CLIENT, SERVER> protected constructor(val namespace: String) {
    protected val clientboundPackets = mutableListOf<PacketDefinition<out NetworkPacket, *>>()
    protected val serverboundPackets = mutableListOf<PacketDefinition<out NetworkPacket, *>>()

    protected val clientHandlers = mutableMapOf<PacketDefinition<out NetworkPacket, *>, MutableList<NetworkHandler<CLIENT, out NetworkPacket>>>()
    protected val serverHandlers = mutableMapOf<PacketDefinition<out NetworkPacket, *>, MutableList<NetworkHandler<SERVER, out NetworkPacket>>>()

    /**
     * Registers packets from server to client.
     */
    open fun <T : NetworkPacket, B : ByteBuf> registerClientbound(id: String, codec: NetworkCodec<T, in B>): PacketDefinition<T, B> {
        if (clientboundPackets.any { it.namespace == namespace && it.id == id })
            throw IllegalArgumentException("Clientbound packet $id already exists!")

        val definition = PacketDefinition(this.namespace, id, codec)
        return registerClientbound(definition)
    }

    /**
     * Registers packets from server to client.
     */
    open fun <T : NetworkPacket, B : ByteBuf> registerClientbound(definition: PacketDefinition<T, B>): PacketDefinition<T, B> {
        if (clientboundPackets.any { it.namespace == definition.namespace && it.id == definition.id })
            throw IllegalArgumentException("Clientbound packet ${definition.namespace}:${definition.id} already exists!")

        clientboundPackets.add(definition)
        return definition
    }

    /**
     * Registers packets from client to server.
     */
    open fun <T : NetworkPacket, B : ByteBuf> registerServerbound(id: String, codec: NetworkCodec<T, in B>): PacketDefinition<T, B> {
        if (serverboundPackets.any { it.namespace == namespace && it.id == id })
            throw IllegalArgumentException("Serverbound packet $id already exists!")

        val definition = PacketDefinition(this.namespace, id, codec)
        return registerServerbound(definition)
    }

    /**
     * Registers packets from client to server.
     */
    open fun <T : NetworkPacket, B : ByteBuf> registerServerbound(definition: PacketDefinition<T, B>): PacketDefinition<T, B> {
        if (clientboundPackets.any { it.namespace == definition.namespace && it.id == definition.id })
            throw IllegalArgumentException("Serverbound packet ${definition.namespace}:${definition.id} already exists!")

        serverboundPackets.add(definition)
        return definition
    }

    /**
     * Registers packets that handle both client to server and server to client.
     */
    open fun <T : NetworkPacket, B : ByteBuf> registerDual(id: String, codec: NetworkCodec<T, in B>): PacketDefinition<T, B> {
        return this.registerClientbound(this.registerServerbound(id, codec))
    }

    /**
     * Registers packets that handle both client to server and server to client.
     */
    open fun <T : NetworkPacket, B : ByteBuf> registerDual(definition: PacketDefinition<T, B>): PacketDefinition<T, B> {
        return this.registerClientbound(this.registerServerbound(definition))
    }

    open fun <T : NetworkPacket, B : ByteBuf> addClientboundHandler(definition: PacketDefinition<T, B>, handler: NetworkHandler<CLIENT, T>) {
        clientHandlers.computeIfAbsent(definition) { _ -> mutableListOf() }
            .add(handler)
    }

    open fun <T : NetworkPacket, B : ByteBuf> addServerboundHandler(definition: PacketDefinition<T, B>, handler: NetworkHandler<SERVER, T>) {
        serverHandlers.computeIfAbsent(definition) { _ -> mutableListOf() }
            .add(handler)
    }

    /**
     * For internal and implementing use only.
     */
    open fun <T : NetworkPacket, B : ByteBuf> handleClientPacket(definition: PacketDefinition<T, B>, buffer: B, ctx: CLIENT): Boolean {
        val packet = definition.codec.decode(buffer)
        return handleClientPacket(definition, packet, ctx)
    }

    /**
     * For internal and implementing use only.
     */
    open fun <T : NetworkPacket, B : ByteBuf> handleClientPacket(definition: PacketDefinition<T, B>, packet: T, ctx: CLIENT): Boolean {
        val handlers = this.clientHandlers[definition] as List<NetworkHandler<CLIENT, T>>? ?: return false

        for (handler in handlers) {
            handler.handle(packet, ctx)
        }

        return true
    }

    /**
     * For internal and implementing use only.
     */
    open fun <T : NetworkPacket, B : ByteBuf> handleServerPacket(definition: PacketDefinition<T, B>, buffer: B, ctx: SERVER): Boolean {
        val packet = definition.codec.decode(buffer)
        return handleServerPacket(definition, packet, ctx)
    }

    /**
     * For internal and implementing use only.
     */
    open fun <T : NetworkPacket, B : ByteBuf> handleServerPacket(definition: PacketDefinition<T, B>, packet: T, ctx: SERVER): Boolean {
        val handlers = this.serverHandlers[definition] as List<NetworkHandler<SERVER, T>>? ?: return false

        for (handler in handlers) {
            handler.handle(packet, ctx)
        }

        return true
    }

    open fun <B : ByteBuf> getClientboundDefinition(path: String): PacketDefinition<out NetworkPacket, B>? {
        return this.clientboundPackets.firstOrNull { it.id == path } as PacketDefinition<out NetworkPacket, B>?
    }

    open fun <B : ByteBuf> getServerboundDefinition(path: String): PacketDefinition<out NetworkPacket, B>? {
        return this.serverboundPackets.firstOrNull { it.id == path } as PacketDefinition<out NetworkPacket, B>?
    }

    fun interface NetworkHandler<C, T : NetworkPacket> {
        fun handle(packet: T, ctx: C)
    }
}