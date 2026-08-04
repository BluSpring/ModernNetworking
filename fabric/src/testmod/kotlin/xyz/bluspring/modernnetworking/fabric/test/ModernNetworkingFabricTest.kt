package xyz.bluspring.modernnetworking.fabric.test

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.network.chat.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftServerPacketHandlers
import xyz.bluspring.modernnetworking.fabric.test.packet.TestClientLoginPacket
import xyz.bluspring.modernnetworking.fabric.test.packet.TestClientPlayPacket
import xyz.bluspring.modernnetworking.fabric.test.packet.TestServerLoginPacket
import xyz.bluspring.modernnetworking.fabric.test.packet.TestServerPlayPacket

class ModernNetworkingFabricTest : ModInitializer {
    override fun onInitialize() {
        MinecraftServerPacketHandlers.LOGIN.registerLogin(TEST_SERVER_LOGIN, { packet, ctx ->

        }, { ctx ->
            logger.info("Received response for ")
        })

        MinecraftServerPacketHandlers.PLAY.register(TEST_SERVER_PLAY) { packet, ctx ->
            ctx.server.submit {
                ctx.player.sendSystemMessage(Component.literal("Received server play ${packet.testString} ${packet.testByteArray.toHexString()}"))
            }
        }

        ServerPlayConnectionEvents.JOIN.register { impl, sender, server ->
            server.execute {
                MinecraftServerPacketHandlers.PLAY.send(impl.player, TestClientPlayPacket("Test Clientbound", byteArrayOf(5, 7, 3, 17, 6, 0)))
            }
        }
    }

    companion object {
        const val MOD_ID = "modernnetworking_test"
        val logger: Logger = LoggerFactory.getLogger("ModernNetworking Test")

        val TEST_SERVER_LOGIN = MinecraftPacketRegistries.SERVER_PLAY.register(MOD_ID, "test_server", TestServerLoginPacket.CODEC)
        val TEST_CLIENT_LOGIN = MinecraftPacketRegistries.CLIENT_PLAY.register(MOD_ID, "test_client", TestClientLoginPacket.CODEC)

        val TEST_SERVER_PLAY = MinecraftPacketRegistries.SERVER_PLAY.register(MOD_ID, "test_server", TestServerPlayPacket.CODEC)
        val TEST_CLIENT_PLAY = MinecraftPacketRegistries.CLIENT_PLAY.register(MOD_ID, "test_client", TestClientPlayPacket.CODEC)
    }
}
