package xyz.bluspring.modernnetworking.neoforge

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
//? if >= 1.20.5 {
import net.neoforged.neoforge.network.event.RegisterConfigurationTasksEvent
//? } else {
/*import net.neoforged.neoforge.network.event.OnGameConfigurationEvent as RegisterConfigurationTasksEvent
*///? }
import xyz.bluspring.modernnetworking.ModernNetworking
import xyz.bluspring.modernnetworking.minecraft.api.v2.packet.MinecraftServerPacketHandlers
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
