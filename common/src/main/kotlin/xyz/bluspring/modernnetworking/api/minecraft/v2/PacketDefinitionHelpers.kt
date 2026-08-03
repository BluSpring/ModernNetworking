package xyz.bluspring.modernnetworking.api.minecraft.v2

import net.minecraft.resources.ResourceLocation
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition

object PacketDefinitionHelpers {
    @JvmStatic
    val PacketDefinition<*, *>.identifier: ResourceLocation
        //? if >= 1.20.5 {
        get() = ResourceLocation.fromNamespaceAndPath(this.namespace, this.id)
        //? } else {
        /*get() = ResourceLocation(this.namespace, this.id)
        *///? }
}
