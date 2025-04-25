# Modern Networking
A multiversioned and multiloader network packet library for Minecraft based on 1.20.6+'s method of packets.

This API has also been designed in such a way that allows for Bukkit implementations, and even implementations outside
of Minecraft, however you will have to provide the backing implementations yourself.

## Installation Guide (for developers)

This guide intends to help explain to developers on what are the primary requirements for installing
Modern Networking, and to understand the idea behind each added dependency.

Gradle (Kotlin)
```kts
repositories {
    maven {
        url = uri("https://mvn.devos.one/releases")
    }
}

dependencies {
    // Please refer to the release tags for the latest version of Modern Networking.
    // Any Minecraft version support will never be dropped from Modern Networking, so you can
    // be comfortable in using the latest version. If anyone requests, I might also provide
    // support for any older versions and/or snapshots as needed. 
    val modernNetworkingVersion = "1.0.0"
    
    // ModernNetworking supports Minecraft versions in a much wider manner, where
    // versions between 1.18.2 and 1.20.1 are able to be supported under one version.
    // You may refer to the table below for the version support.
    val minecraftVersion = "1.18.2"
    
    // This is the API. You would want this to get the sources for it, or if you want to
    // create your own implementation, as the API is fully disconnected from Minecraft itself.
    implementation("xyz.bluspring.modernnetworking:modernnetworking-api:$modernNetworkingVersion")

    // The mod loader type. Valid types:
    // - fabric
    // - forge
    // - neoforge (only on 1.20.4 and above!)
    val modLoader = "fabric"
    
    // This is the shared library for both modloaders. I recommend adding this for having sources data,
    // even if you do not use a multiloader setup.
    modImplementation("xyz.bluspring.modernnetworking:modernnetworking-common:$modernNetworkingVersion+$minecraftVersion")
    
    // This is the modloader-specific library. You may bundle this with your mod if you want.
    // Please note that if you bundle this mod, you may create an additional dependency on
    // Fabric Language Kotlin if you are using Fabric, or increase your mod's JAR size due to the
    // Forge/NeoForge-based libraries bundling Kotlin.
    modImplementation("xyz.bluspring.modernnetworking:modernnetworking-$modLoader:$modernNetworkingVersion+$minecraftVersion")
}
```

| Library Minecraft Version | Supported Minecraft Versions |
|---------------------------|------------------------------|
| 1.18.2                    | 1.18.2 - 1.20.1              |
| 1.20.4                    | 1.20.2 - 1.20.4              |
| 1.20.6                    | 1.20.5 - 1.20.6              |
| 1.21.1                    | 1.21 - Latest                |

## Usage

Java
```java
// Create your custom packet, like the 1.20.6+ way of doing so.
public record CustomPacket(String data) implements NetworkPacket {
    public static final NetworkCodec<CustomPacket, FriendlyByteBuf> CODEC = CompositeCodecs.composite(
        NetworkCodecs.STRING_UTF8, CustomPacket::data,
        CustomPacket::new
    );
    
    @Override
    public PacketDefinition<? extends NetworkPacket, ? extends ByteBuf> getDefinition() {
        return YourModClass.CUSTOM_PACKET; 
    }
}

// Create the network registry for your namespace.
private static final VanillaRegistry registry = VanillaRegistry.create("modid");

// Create a clientbound (server -> client) definition for your custom packet.
// This should preferably be a public static final field.
// If desired, you may also specify a definition directly rather than the ID and codec.
public static final PacketDefinition<CustomPacket, FriendlyByteBuf> CUSTOM_PACKET = registry.registerClientbound("custom_packet", CustomPacket.CODEC);

// Register client network handler for this custom packet.
registry.addClientboundHandler(CUSTOM_PACKET, (packet, ctx) -> {
    // Handle your logic here.
    // Remember that this is running under the **network thread**,
    // not the client/server thread.
});

// Send packet from server to client.
VanillaPacketSender.sendToPlayer(serverPlayer, new CustomPacket("Your custom data here"));
```

Kotlin
```kotlin
// Create your custom packet, like the 1.20.6+ way of doing so.
data class CustomPacket(val data: String) : NetworkPacket {
    override fun getDefinition(): PacketDefinition<out NetworkPacket, out ByteBuf> {
        return YourModClass.CUSTOM_PACKET
    }
    
    companion object {
        val CODEC = CompositeCodecs.composite(
            NetworkCodecs.STRING_UTF8, CustomPacket::data,
            ::CustomPacket
        )
    }
}

// Create the network registry for your namespace.
private val registry = VanillaRegistry.create("modid")

// Create a clientbound (server -> client) definition for your custom packet.
// This should preferably be a public static final field.
// If desired, you may also specify a definition directly rather than the ID and codec.
val CUSTOM_PACKET = registry.registerClientbound("custom_packet", CustomPacket.CODEC)

// Register client network handler for this custom packet.
registry.addClientboundHandler(CUSTOM_PACKET) { packet, ctx ->
    // Handle your logic here.
    // Remember that this is running under the **network thread**,
    // not the client/server thread.
}

// Send packet from server to client.
VanillaPacketSender.sendToPlayer(serverPlayer, CustomPacket("Your custom data here"))
```