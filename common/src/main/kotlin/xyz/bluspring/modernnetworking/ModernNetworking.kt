package xyz.bluspring.modernnetworking

//? if < 1.21.11 {
import net.minecraft.resources.ResourceLocation
//?} else {
/*import net.minecraft.resources.Identifier as ResourceLocation
*///?}

object ModernNetworking {
    fun id(namespace: String, path: String): ResourceLocation {
        //? if >= 1.21 {
        /*return ResourceLocation.fromNamespaceAndPath(namespace, path)
        *///?} else {
        return ResourceLocation(namespace, path)
        //?}
    }
}