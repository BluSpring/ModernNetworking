package xyz.bluspring.modernnetworking.mixin;

//? if >= 1.20.6 {
/*import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import xyz.bluspring.modernnetworking.modern.CustomPayloadWrapper;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.bluspring.modernnetworking.api.minecraft.VanillaNetworkRegistry;

import java.util.ArrayList;
*///?}

//? if >= 1.20.6 {
/*@Mixin(ClientboundCustomPayloadPacket.class)
*///?}
public abstract class ClientboundCustomPayloadPacketMixin {
    //? if >= 1.20.6 {
    /*@Inject(method = "method_56461", at = @At("HEAD"), cancellable = true)
    private static <B, V> void modernnetworking$addRegisteredPacketCodecs(ResourceLocation location, CallbackInfoReturnable<StreamCodec<B, V>> cir) {
        var registry = VanillaNetworkRegistry.get(location.getNamespace());

        if (registry != null) {
            var codec = registry.getClientCodecs().get(location.getPath());

            if (codec != null) {
                cir.setReturnValue((StreamCodec<B, V>) codec);
            }
        }
    }

    @Inject(method = "method_58270", at = @At("HEAD"))
    private static void modernnetworking$addRegisteredPacketTypes(ArrayList<CustomPacketPayload.TypeAndCodec<? super RegistryFriendlyByteBuf, ? extends CustomPacketPayload>> list, CallbackInfo ci) {
        for (@NotNull String namespace : VanillaNetworkRegistry.Companion.getRegistries().keySet()) {
            var registry = VanillaNetworkRegistry.get(namespace);

            for (@NotNull String path : registry.getServerTypes().keySet()) {
                var type = registry.getClientTypes().get(path);
                var codec = registry.getClientCodecs().get(path);

                list.add(new CustomPacketPayload.TypeAndCodec<>((CustomPacketPayload.Type<CustomPayloadWrapper<?>>) type, (StreamCodec<RegistryFriendlyByteBuf, CustomPayloadWrapper<?>>) codec));
            }
        }
    }
    *///?}
}
