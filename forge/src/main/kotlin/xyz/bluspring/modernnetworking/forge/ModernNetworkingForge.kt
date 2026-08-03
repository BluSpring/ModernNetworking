package xyz.bluspring.modernnetworking.forge

import net.minecraftforge.fml.common.Mod
import xyz.bluspring.modernnetworking.ModernNetworking

@Mod(ModernNetworking.MOD_ID)
class ModernNetworkingForge {
    init {
        ModernNetworking.init()
    }
}
