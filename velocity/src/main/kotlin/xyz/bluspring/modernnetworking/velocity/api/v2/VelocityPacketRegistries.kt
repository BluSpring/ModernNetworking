package xyz.bluspring.modernnetworking.velocity.api.v2

import com.velocitypowered.api.proxy.ProxyServer

object VelocityPacketRegistries {
    private lateinit var proxy: ProxyServer

    @JvmStatic
    val clientPlayRegistry by lazy {
        this.ensureProxyServerAvailable()
        VelocityPacketRegistry(this.proxy)
    }

    @JvmStatic
    val serverPlayRegistry by lazy {
        this.ensureProxyServerAvailable()
        VelocityPacketRegistry(this.proxy)
    }

    /**
     * This is used to ensure the [ProxyServer] instance used by each packet registry is still available.
     * Otherwise, Modern Networking may be unable to inform Velocity that the packets exist in the first place,
     * depending on when Modern Networking's plugin gets initialized and when your plugin does.
     */
    @JvmStatic
    fun initProxyServer(proxy: ProxyServer) {
        this.proxy = proxy
    }

    private fun ensureProxyServerAvailable() {
        if (!this::proxy.isInitialized)
            throw IllegalStateException("Modern Networking's stored proxy instance has not yet been initialized! If you are the developer of a Velocity plugin using Modern Networking, please make sure you are calling VelocityPacketRegistries.initProxyServer!")
    }
}
