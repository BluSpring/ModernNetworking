package xyz.bluspring.modernnetworking.neoforge.mixin;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientCommonPacketListener;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerCommonPacketListener;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.neoforged.neoforge.network.registration.NetworkRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.bluspring.modernnetworking.modern.CustomPayloadWrapper;

@Mixin(NetworkRegistry.class)
public class NetworkRegistryMixin {
    //? if >= 1.20.5 {
    /*// Avoids crashes on NeoForge with our custom packets
    @Inject(method = "checkPacket(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/protocol/common/ClientCommonPacketListener;)V", at = @At("HEAD"), cancellable = true)
    private static void modernnetworking$avoidPacketCrashClient(Packet<?> packet, ClientCommonPacketListener listener, CallbackInfo ci) {
        if (packet instanceof ServerboundCustomPayloadPacket customPayloadPacket) {
            if (customPayloadPacket.payload() instanceof CustomPayloadWrapper<?>)
                ci.cancel();
        }
    }

    @Inject(method = "checkPacket(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/protocol/common/ServerCommonPacketListener;)V", at = @At("HEAD"), cancellable = true)
    private static void modernnetworking$avoidPacketCrashClient(Packet<?> packet, ServerCommonPacketListener listener, CallbackInfo ci) {
        if (packet instanceof ClientboundCustomPayloadPacket customPayloadPacket) {
            if (customPayloadPacket.payload() instanceof CustomPayloadWrapper<?>)
                ci.cancel();
        }
    }
    *///?}
}
