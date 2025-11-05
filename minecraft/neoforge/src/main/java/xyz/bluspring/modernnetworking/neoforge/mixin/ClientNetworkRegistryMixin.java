package xyz.bluspring.modernnetworking.neoforge.mixin;

//? if >= 1.21.6 {
/*import net.minecraft.network.protocol.common.ClientCommonPacketListener;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.client.network.registration.ClientNetworkRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.bluspring.modernnetworking.modern.CustomPayloadWrapper;

@Mixin(ClientNetworkRegistry.class)
*///?}
public class ClientNetworkRegistryMixin {
    //? if >= 1.21.6 {
    /*@Inject(method = "handleModdedPayload(Lnet/minecraft/network/protocol/common/ClientCommonPacketListener;Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket;)V", at = @At("HEAD"), cancellable = true)
    private static void modernnetworking$avoidPacketCrash(ClientCommonPacketListener listener, ClientboundCustomPayloadPacket packet, CallbackInfo ci) {
        modernnetworking$checkIsModernNetworkingPayload(packet.payload(), ci);
    }

    @Unique
    private static void modernnetworking$checkIsModernNetworkingPayload(CustomPacketPayload payload, CallbackInfo ci) {
        if (payload instanceof CustomPayloadWrapper<?>)
            ci.cancel();
    }
    *///?}
}
