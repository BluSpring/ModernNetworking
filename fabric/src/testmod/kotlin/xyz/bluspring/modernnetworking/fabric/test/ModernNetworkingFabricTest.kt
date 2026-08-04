package xyz.bluspring.modernnetworking.fabric.test

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.network.chat.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftServerPacketHandlers
import xyz.bluspring.modernnetworking.fabric.api.v2.FabricPacketSender.sendPacket
import xyz.bluspring.modernnetworking.fabric.test.packet.TestClientLoginPacket
import xyz.bluspring.modernnetworking.fabric.test.packet.TestClientPlayPacket
import xyz.bluspring.modernnetworking.fabric.test.packet.TestServerLoginPacket
import xyz.bluspring.modernnetworking.fabric.test.packet.TestServerPlayPacket

class ModernNetworkingFabricTest : ModInitializer {
    override fun onInitialize() {
        MinecraftServerPacketHandlers.LOGIN.registerLogin(TEST_LOGIN, { packet, ctx ->
            logger.info("Received response from ${ctx.handler.userName} for test login packet on server: ${packet.testString} ${packet.testByteArray.toHexString()}")
        }, { ctx ->
            logger.info("${ctx.handler.userName} doesn't seem to understand the test login packet.")
        })

        MinecraftServerPacketHandlers.PLAY.register(TEST_SERVER_PLAY) { packet, ctx ->
            ctx.server.submit {
                ctx.player.sendSystemMessage(Component.literal("Received server play ${packet.testString} ${packet.testByteArray.toHexString()}"))
            }
        }

        ServerLoginConnectionEvents.QUERY_START.register { impl, server, sender, synchronizer ->
            sender.sendPacket(TestClientLoginPacket("Test clientbound login", byteArrayOf(1, 2, 7, 3)))
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

        val TEST_LOGIN = MinecraftPacketRegistries.LOGIN.register(MOD_ID, "test_login", TestServerLoginPacket.CODEC, TestClientLoginPacket.CODEC)

        val TEST_SERVER_PLAY = MinecraftPacketRegistries.SERVER_PLAY.register(MOD_ID, "test_server", TestServerPlayPacket.CODEC)
        val TEST_CLIENT_PLAY = MinecraftPacketRegistries.CLIENT_PLAY.register(MOD_ID, "test_client", TestClientPlayPacket.CODEC)
    }
}
