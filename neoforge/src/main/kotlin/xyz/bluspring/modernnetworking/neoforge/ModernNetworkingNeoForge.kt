package xyz.bluspring.modernnetworking.neoforge

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.network.event.RegisterConfigurationTasksEvent
import xyz.bluspring.modernnetworking.ModernNetworking
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftServerPacketHandlers
import xyz.bluspring.modernnetworking.neoforge.packet.NeoForgeServerConfigurationPacketHandlerRegistry

@Mod(ModernNetworking.MOD_ID)
class ModernNetworkingNeoForge(bus: IEventBus) {
    init {
        ModernNetworking.init()

        bus.addListener<RegisterConfigurationTasksEvent> { event ->
            (MinecraftServerPacketHandlers.CONFIGURATION as NeoForgeServerConfigurationPacketHandlerRegistry)
                .handleRegisterEvent(event)
        }
    }
}
