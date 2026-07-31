package xyz.bluspring.modernnetworking.api.minecraft.v2

import net.minecraft.resources.ResourceLocation
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition

object PacketDefinitionHelpers {
    @JvmStatic
    val PacketDefinition<*, *>.identifier: ResourceLocation
        get() = ResourceLocation.fromNamespaceAndPath(this.namespace, this.id)
}
