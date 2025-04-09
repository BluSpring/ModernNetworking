package xyz.bluspring.modernnetworking.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
//? if >= 1.20.2 {

import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import xyz.bluspring.modernnetworking.modern.CustomPayloadWrapper;
import xyz.bluspring.modernnetworking.api.NetworkPacket;
import xyz.bluspring.modernnetworking.api.PacketDefinition;
//?} else {

/*import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
 
*///?}
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.bluspring.modernnetworking.api.minecraft.VanillaClientContext;
import xyz.bluspring.modernnetworking.api.minecraft.VanillaNetworkRegistry;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin /*? if >= 1.20.2 {*/extends ClientCommonPacketListenerImpl/*?}*/ {
    //? if < 1.20.2 {
    /*@Shadow @Final private Minecraft minecraft;
    *///?} else {
    protected ClientPacketListenerMixin(Minecraft minecraft, Connection connection, CommonListenerCookie commonListenerCookie) {
        super(minecraft, connection, commonListenerCookie);
    }
    //?}

    @WrapOperation(method = "handleCustomPayload", at = @At(value = "INVOKE", target =
        //? if < 1.20.2 {
        /*"Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;)V"
        *///?} else {
         "Lnet/minecraft/client/multiplayer/ClientPacketListener;handleUnknownCustomPayload(Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V" 
        //?}
    ))
    private void modernnetworking$handleRegisteredPackets(
        //? if < 1.20.2 {
        /*Logger instance, String s, Object o, Operation<Void> original, @Local ResourceLocation location, @Local FriendlyByteBuf data
        *///?} else {
        ClientPacketListener instance, CustomPacketPayload payload, Operation<Void> original
        //?}
    ) {
        //? if >= 1.20.2 {
        if (!(payload instanceof CustomPayloadWrapper<?> wrapper))
            return;

        //? if >= 1.20.6 {
        /*var location = payload.type().id();*/
        //? } else if >= 1.20.2 {
        var location = payload.id();
        //? }
        var data = wrapper.getPacket();
        //?}
        var registry = VanillaNetworkRegistry.get(location.getNamespace());

        if (registry != null) {
            var definition = registry.getClientboundDefinition(location.getPath());

            if (definition != null) {
                var ctx = new VanillaClientContext(this.minecraft, this.minecraft.player);
                registry.handleClientPacket(/*? if >= 1.20.2 {*/(PacketDefinition<? super NetworkPacket, ? extends ByteBuf>)/*?}*/ definition, data, ctx);

                return;
            }
        }

        //? if < 1.20.2 {
        /*original.call(instance, s, o);
        *///?} else {
        original.call(instance, payload);
        //?}
    }
}
