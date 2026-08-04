package xyz.bluspring.modernnetworking.fabric.test.client

import net.fabricmc.api.ClientModInitializer
//? if >= 1.20 {
/*import net.minecraft.network.chat.Component
*///? } else {
import net.minecraft.network.chat.TextComponent
//? }
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.MinecraftClientPacketHandlers
import xyz.bluspring.modernnetworking.fabric.test.ModernNetworkingFabricTest
import xyz.bluspring.modernnetworking.fabric.test.packet.TestServerLoginPacket
import xyz.bluspring.modernnetworking.fabric.test.packet.TestServerPlayPacket
import java.util.concurrent.CompletableFuture

class ModernNetworkingFabricTestClient : ClientModInitializer {
    override fun onInitializeClient() {
        MinecraftClientPacketHandlers.LOGIN.registerLogin(ModernNetworkingFabricTest.TEST_LOGIN) { packet, ctx ->
            ModernNetworkingFabricTest.logger.info("Received login packet w/ contents ${packet.testString} & ${packet.testByteArray.toHexString()}")
            CompletableFuture.completedFuture(TestServerLoginPacket("Test Serverbound Login", byteArrayOf(4, 4, 1, 3)))
        }

        MinecraftClientPacketHandlers.PLAY.register(ModernNetworkingFabricTest.TEST_CLIENT_PLAY) { packet, ctx ->
            ctx.client.gui.chat.addMessage(
                //? if >= 1.20 {
                /*Component.literal
                *///? } else {
                TextComponent
                //? }
                ("Received client play ${packet.testString} ${packet.testByteArray.toHexString()}"))
            MinecraftClientPacketHandlers.PLAY.send(TestServerPlayPacket("Test serverbound", byteArrayOf(4, 4, 2, 1, 7, 5, 2, 3, 0)))
        }
    }
}
