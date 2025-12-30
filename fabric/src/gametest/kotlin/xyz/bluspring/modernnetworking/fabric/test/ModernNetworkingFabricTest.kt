package xyz.bluspring.modernnetworking.fabric.test

import net.minecraft.SharedConstants
import net.minecraft.server.Bootstrap
import org.junit.jupiter.api.BeforeAll

class ModernNetworkingFabricTest : CustomTestMethodInvoker {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            SharedConstants.tryDetectVersion()
            Bootstrap.bootStrap()

        }
    }
}