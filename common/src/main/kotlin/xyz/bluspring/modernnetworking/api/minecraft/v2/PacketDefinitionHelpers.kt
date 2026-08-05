package xyz.bluspring.modernnetworking.api.minecraft.v2

//? if >= 1.21.11 {
/*import net.minecraft.resources.Identifier as ResourceLocation
*///? } else {
import net.minecraft.resources.ResourceLocation
//? }
import xyz.bluspring.modernnetworking.api.v2.packet.PacketDefinition

object PacketDefinitionHelpers {
    @JvmStatic
    val PacketDefinition<*, *>.identifier: ResourceLocation
        //? if >= 1.21 {
        /*get() = ResourceLocation.fromNamespaceAndPath(this.namespace, this.id)
        *///? } else {
        get() = ResourceLocation(this.namespace, this.id)
        //? }
}
