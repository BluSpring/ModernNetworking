package xyz.bluspring.modernnetworking.fabric.test

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.chat.Component
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftPacketRegistries
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.MinecraftServerPacketHandlers
import xyz.bluspring.modernnetworking.api.minecraft.v2.packet.client.MinecraftClientPacketHandlers
import xyz.bluspring.modernnetworking.fabric.test.packet.TestClientPacket
import xyz.bluspring.modernnetworking.fabric.test.packet.TestServerPacket

class ModernNetworkingFabricTest : ModInitializer {
    override fun onInitialize() {
        MinecraftServerPacketHandlers.PLAY.register(TEST_SERVER_PLAY) { packet, ctx ->
            ctx.server.submit {
                ctx.player.sendSystemMessage(Component.literal("Received server play ${packet.testString} ${packet.testByteArray}"))
            }
        }

        ServerPlayConnectionEvents.JOIN.register { impl, sender, server ->
            server.execute {
                MinecraftServerPacketHandlers.PLAY.send(impl.player, TestClientPacket("Test Clientbound", byteArrayOf(5, 7, 3, 17, 6, 0)))
            }
        }
    }

    companion object {
        const val MOD_ID = "modernnetworking_test"
        val TEST_SERVER_PLAY = MinecraftPacketRegistries.SERVER_PLAY.register(MOD_ID, "test_server", TestServerPacket.CODEC)
        val TEST_CLIENT_PLAY = MinecraftPacketRegistries.CLIENT_PLAY.register(MOD_ID, "test_client", TestClientPacket.CODEC)
    }
}
