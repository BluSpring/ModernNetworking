package xyz.bluspring.modernnetworking.mixin;

import net.minecraft.network.protocol./*? if < 1.20.2 {*//*game*//*?} else {*/common/*?}*/.ServerboundCustomPayloadPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
//? if >= 1.20.2 {
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
//?}
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.bluspring.modernnetworking.api.minecraft.VanillaNetworkRegistry;
import xyz.bluspring.modernnetworking.api.minecraft.VanillaServerContext;
//? if >= 1.20.2 {

import io.netty.buffer.ByteBuf;
import net.minecraft.network.Connection;
import net.minecraft.server.network.CommonListenerCookie;
import xyz.bluspring.modernnetworking.api.NetworkPacket;
import xyz.bluspring.modernnetworking.api.PacketDefinition;
import xyz.bluspring.modernnetworking.modern.CustomPayloadWrapper;

//?}

//? if < 1.20.6 {
/*import org.spongepowered.asm.mixin.Final;
*///?}

@Mixin(/*? if <1.20.6 >=1.20.2 {*//*ServerCommonPacketListenerImpl.class*//*?} else {*/ServerGamePacketListenerImpl.class/*?}*/)
public abstract class ServerGamePacketListenerImplMixin /*? if >= 1.20.6 {*/extends ServerCommonPacketListenerImpl/*?}*/ {
    /*? if < 1.20.6 {*//*@Shadow @Final private MinecraftServer server;*//*?}*/

    //? if >= 1.20.6 {
    public ServerGamePacketListenerImplMixin(MinecraftServer server, Connection connection, CommonListenerCookie cookie) {
        super(server, connection, cookie);
    }
    //?}

    @Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
    private void modernnetworking$handleRegisteredPackets(ServerboundCustomPayloadPacket packet, CallbackInfo ci) {
        //? if < 1.20.2 {
        /*var location = packet.getIdentifier();
        var data = packet.getData();
        *///?} else {
        if (!(packet.payload() instanceof CustomPayloadWrapper<?> wrapper))
            return;

        //? if >= 1.20.6 {
        var location = wrapper.type().id();
        //?} else {
        /*var location = wrapper.id();
        *///?}
        var data = wrapper.getPacket();
        //?}

        var registry = VanillaNetworkRegistry.get(location.getNamespace());

        if (registry != null) {
            var definition = registry.getServerboundDefinition(location.getPath());

            if (definition != null) {
                try {
                    var ctx = new VanillaServerContext(this.server, ((ServerGamePacketListenerImpl) (Object) this).player);
                    registry.handleServerPacket(/*? if >= 1.20.2 {*/(PacketDefinition<? super NetworkPacket, ? extends ByteBuf>)/*?}*/ definition, data, ctx);
                } finally {
                    ci.cancel();
                }
            }
        }
    }
}
