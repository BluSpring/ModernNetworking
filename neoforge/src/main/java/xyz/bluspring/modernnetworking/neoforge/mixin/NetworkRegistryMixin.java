package xyz.bluspring.modernnetworking.neoforge.mixin;

//? if >= 1.20.5 {
/*import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientCommonPacketListener;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerCommonPacketListener;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.bluspring.modernnetworking.modern.CustomPayloadWrapper;
*///?}
import net.neoforged.neoforge.network.registration.NetworkRegistry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(NetworkRegistry.class)
public class NetworkRegistryMixin {
    //? if >= 1.20.5 {
    /*// Avoids crashes on NeoForge with our custom packets
    @Inject(method = "checkPacket(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/protocol/common/ClientCommonPacketListener;)V", at = @At("HEAD"), cancellable = true)
    private static void modernnetworking$avoidPacketCrash(Packet<?> packet, ClientCommonPacketListener listener, CallbackInfo ci) {
        if (packet instanceof ServerboundCustomPayloadPacket customPayloadPacket) {
            modernnetworking$checkIsModernNetworkingPayload(customPayloadPacket.payload(), ci);
        }
    }

    @Inject(method = "checkPacket(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/protocol/common/ServerCommonPacketListener;)V", at = @At("HEAD"), cancellable = true)
    private static void modernnetworking$avoidPacketCrash(Packet<?> packet, ServerCommonPacketListener listener, CallbackInfo ci) {
        if (packet instanceof ClientboundCustomPayloadPacket customPayloadPacket) {
            modernnetworking$checkIsModernNetworkingPayload(customPayloadPacket.payload(), ci);
        }
    }

    //? if < 1.21.6 {
    @Inject(method = "handleModdedPayload(Lnet/minecraft/network/protocol/common/ClientCommonPacketListener;Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket;)V", at = @At("HEAD"), cancellable = true)
    private static void modernnetworking$avoidPacketCrash(ClientCommonPacketListener listener, ClientboundCustomPayloadPacket packet, CallbackInfo ci) {
        modernnetworking$checkIsModernNetworkingPayload(packet.payload(), ci);
    }
    
    //?}

    @Inject(method = "handleModdedPayload(Lnet/minecraft/network/protocol/common/ServerCommonPacketListener;Lnet/minecraft/network/protocol/common/ServerboundCustomPayloadPacket;)V", at = @At("HEAD"), cancellable = true)
    private static void modernnetworking$avoidPacketCrash(ServerCommonPacketListener listener, ServerboundCustomPayloadPacket packet, CallbackInfo ci) {
        modernnetworking$checkIsModernNetworkingPayload(packet.payload(), ci);
    }

    @Unique
    private static void modernnetworking$checkIsModernNetworkingPayload(CustomPacketPayload payload, CallbackInfo ci) {
        if (payload instanceof CustomPayloadWrapper<?>)
            ci.cancel();
    }

    *///?}
}
