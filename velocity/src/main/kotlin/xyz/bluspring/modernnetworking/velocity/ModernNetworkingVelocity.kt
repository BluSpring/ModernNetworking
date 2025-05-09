package xyz.bluspring.modernnetworking.velocity

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.PluginMessageEvent
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.ServerConnection
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import org.slf4j.Logger
import xyz.bluspring.modernnetworking.velocity.api.RegisterNetworkRegistryEvent
import xyz.bluspring.modernnetworking.velocity.api.VelocityNetworkContext
import xyz.bluspring.modernnetworking.velocity.api.VelocityNetworkRegistry

@Plugin(
    id = "modernnetworking",
    name = "ModernNetworking",
    version = BuildConstants.VERSION,
    description = "Multiversioned and multiloader network packet API based on 1.20.6+'s method of packets.",
    authors = ["BluSpring"],
    url = "https://github.com/BluSpring/ModernNetworking"
)
class ModernNetworkingVelocity @Inject constructor(
    val proxy: ProxyServer
) {
    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent) {
        proxy.eventManager.fireAndForget(RegisterNetworkRegistryEvent(proxy))
    }

    @Subscribe
    fun onPluginMessage(event: PluginMessageEvent) {
        val identifier = event.identifier
        if (identifier !is MinecraftChannelIdentifier)
            return

        val registry = VelocityNetworkRegistry.get(identifier.namespace) ?: return

        if (event.source is Player && event.target is ServerConnection) { // this should be C -> S
            val definition = registry.getServerboundDefinition<ByteBuf>(identifier.name) ?: return
            val buffer = Unpooled.buffer()

            if (event.data != null)
                buffer.writeBytes(event.data)

            registry.handleServerPacket(definition, buffer, VelocityNetworkContext(event.source as Player, proxy, event.target as ServerConnection))
            event.result = PluginMessageEvent.ForwardResult.handled()
        } else if (event.source is ServerConnection && event.target is Player) { // this should be S -> C
            val definition = registry.getClientboundDefinition<ByteBuf>(identifier.name) ?: return
            val buffer = Unpooled.buffer()

            if (event.data != null)
                buffer.writeBytes(event.data)

            registry.handleClientPacket(definition, buffer, VelocityNetworkContext(event.target as Player, proxy, event.source as ServerConnection))
            event.result = PluginMessageEvent.ForwardResult.handled()
        }
    }
}