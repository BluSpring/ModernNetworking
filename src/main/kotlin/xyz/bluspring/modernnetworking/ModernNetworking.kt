package xyz.bluspring.modernnetworking

import net.minecraft.resources.ResourceLocation

object ModernNetworking {
    fun id(namespace: String, path: String): ResourceLocation {
        //? if >= 1.21 {
        /*return ResourceLocation.fromNamespaceAndPath(namespace, path)
        *///?} else {
        return ResourceLocation(namespace, path)
        //?}
    }
}