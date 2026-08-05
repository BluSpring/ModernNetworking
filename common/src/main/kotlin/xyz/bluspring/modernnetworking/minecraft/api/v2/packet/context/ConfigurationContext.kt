package xyz.bluspring.modernnetworking.minecraft.api.v2.packet.context

import net.minecraft.network.protocol.Packet
import org.jetbrains.annotations.ApiStatus
import xyz.bluspring.modernnetworking.api.v2.packet.NetworkPacket
import java.util.function.Consumer

class ConfigurationContext<R> @ApiStatus.Internal constructor (
    val namespace: String,
    val id: String,
    val taskId: String,
    val receiver: R,
    private val completionListener: () -> Unit,
) {
    @ApiStatus.Internal private lateinit var taskPacketConsumer: (Any) -> Unit

    @ApiStatus.Internal
    fun startConfigurationTask(consumer: (Any) -> Unit) {
        this.taskPacketConsumer = consumer
    }

    fun send(packet: Packet<*>) {
        this@ConfigurationContext.taskPacketConsumer(packet)
    }

    fun send(packet: NetworkPacket) {
        this@ConfigurationContext.taskPacketConsumer(packet)
    }

    /**
     * Marks this task as completed, allowing the receiver [R] to continue
     */
    fun complete() {
        this.completionListener()
    }
}
