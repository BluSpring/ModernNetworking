package xyz.bluspring.modernnetworking.fabric.test.client

import net.fabricmc.api.ClientModInitializer
import net.minecraft.network.chat.Component
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.MinecraftClientPacketHandlers
import xyz.bluspring.modernnetworking.fabric.test.ModernNetworkingFabricTest
import xyz.bluspring.modernnetworking.fabric.test.packet.TestServerLoginPacket
import xyz.bluspring.modernnetworking.fabric.test.packet.TestServerPlayPacket

class ModernNetworkingFabricTestClient : ClientModInitializer {
    override fun onInitializeClient() {
        MinecraftClientPacketHandlers.LOGIN.register(ModernNetworkingFabricTest.TEST_CLIENT_LOGIN) { packet, ctx ->
            ModernNetworkingFabricTest.logger.info("Received login packet w/ contents ${packet.testString} & ${packet.testByteArray.toHexString()}")
            MinecraftClientPacketHandlers.LOGIN.send(TestServerLoginPacket("Test Serverbound Login", byteArrayOf(4, 4, 1, 3)))
        }

        MinecraftClientPacketHandlers.PLAY.register(ModernNetworkingFabricTest.TEST_CLIENT_PLAY) { packet, ctx ->
            ctx.client.execute {
                ctx.client.gui.chat.addMessage(Component.literal("Received client play ${packet.testString} ${packet.testByteArray.toHexString()}"))
                MinecraftClientPacketHandlers.PLAY.send(TestServerPlayPacket("Test serverbound", byteArrayOf(4, 4, 2, 1, 7, 5, 2, 3, 0)))
            }
        }
    }
}
