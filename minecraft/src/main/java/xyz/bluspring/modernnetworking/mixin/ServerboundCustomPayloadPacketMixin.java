package xyz.bluspring.modernnetworking.mixin;

//? if >= 1.20.6 {
/*import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
*///?}

//? if >= 1.20.2 {
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
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
//?}

//? if >= 1.20.2 {
@Mixin(ServerboundCustomPayloadPacket.class)
//?}
public abstract class ServerboundCustomPayloadPacketMixin {
    //? if >= 1.20.6 {
    /*@Inject(method = "method_56475", at = @At("HEAD"), cancellable = true)
    private static <B, V> void modernnetworking$addRegisteredPacketCodecs(ResourceLocation location, CallbackInfoReturnable<StreamCodec<B, V>> cir) {
        var registry = VanillaNetworkRegistry.get(location.getNamespace());

        if (registry != null) {
            var codec = registry.getServerCodecs().get(location.getPath());

            if (codec != null) {
                cir.setReturnValue((StreamCodec<B, V>) codec);
            }
        }
    }

    @Inject(method = "method_58271", at = @At("HEAD"))
    private static void modernnetworking$addRegisteredPacketTypes(ArrayList<CustomPacketPayload.TypeAndCodec<? super RegistryFriendlyByteBuf, ? extends CustomPacketPayload>> list, CallbackInfo ci) {
        for (@NotNull String namespace : VanillaNetworkRegistry.Companion.getRegistries().keySet()) {
            var registry = VanillaNetworkRegistry.get(namespace);

            for (@NotNull String path : registry.getServerTypes().keySet()) {
                var type = registry.getServerTypes().get(path);
                var codec = registry.getServerCodecs().get(path);

                list.add(new CustomPacketPayload.TypeAndCodec<>((CustomPacketPayload.Type<CustomPayloadWrapper<?>>) type, (StreamCodec<RegistryFriendlyByteBuf, CustomPayloadWrapper<?>>) codec));
            }
        }
    }
    *///?} else if >= 1.20.2 {
    @Inject(method = "readPayload", at = @At("HEAD"), cancellable = true)
    private static void modernnetworking$readWithRegisteredPacket(ResourceLocation location, FriendlyByteBuf buffer, CallbackInfoReturnable<CustomPacketPayload> cir) {
        var registry = VanillaNetworkRegistry.get(location.getNamespace());

        if (registry != null) {
            var definition = registry.getServerboundDefinition(location.getPath());

            if (definition != null) {
                cir.setReturnValue(new CustomPayloadWrapper<>(definition.getCodec().decode(buffer)));
            }
        }
    }
    //?}
}
