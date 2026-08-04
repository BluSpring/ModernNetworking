# Modern Networking
A multiversioned and multiloader network packet library for Minecraft based on 1.20.6+'s method of packets.

This API has also been designed in such a way that allows for Bukkit implementations, and even implementations outside
of Minecraft, however you will have to provide some of the backing implementations yourself.

This API is very heavily engineered towards Netty. You can make a fork of this codebase to adapt it
outside of Netty if you need it.

## Migrating API from v1 to v2
I've taken great care in trying to maintain binary compatibility with the original v1 API, so any projects
still using Modern Networking v1's API will not have to worry about broken API compatibility.

The main changes:
- Switched the generics for NetworkCodec
- Implemented support for login and configuration phases
  - The login phase's implementation is very rough, largely due to the heavy inconsistencies
    between Fabric, Forge and NeoForge on its implementation.
  - NeoForge also doesn't actually support the login phase, annoyingly. Neither does Bukkit.
  - Bukkit/Paper also doesn't support the configuration phase system.
- Added defaulted handler and packet registry implementations for easier use in projects outside of Minecraft
- Heavily improved the API with Bukkit
- Added some useful helpers into NetworkCodec
  - e.g. `xmap`, `dispatch`
- Rewrote some `NetworkCodecs` helpers to be more Kotlin-friendly while still being usable by Java.
- All network handlers now register directly to the implementing mod loader's API
  - This subsequently fixes Bukkit not being able to send/receive packets from Modern Networking.

## Installation Guide (for developers)

This guide intends to help explain to developers on what are the primary requirements for installing
Modern Networking, and to understand the idea behind each added dependency. This guide uses Gradle under the Kotlin
buildscript, however it can very easily be adapted to the Groovy buildscript. (just change `val` to `def`)

### Standard Development Setup
This development setup is to be used by both non-modded and modded development environments.
### 
```kts
repositories {
    maven {
        url = uri("https://mvn.devos.one/releases")
    }
}

// Please refer to the release tags for the latest version of Modern Networking.
// Any Minecraft version support will never be dropped from Modern Networking, so you can
// be comfortable in using the latest version. If anyone requests, I might also provide
// support for any older versions and/or snapshots as needed. 
val modernNetworkingVersion = "2.0.0"

// ModernNetworking supports Minecraft versions in a much wider manner, where
// versions between 1.18.2 and 1.20.1 are able to be supported under one version.
// You may refer to the table below for the version support.
val minecraftVersion = "1.18.2"

dependencies {
    // This is the API. You would want this to get the sources for it, or if you want to
    // create your own implementation, as the API is fully disconnected from Minecraft itself.
    implementation("xyz.bluspring.modernnetworking:modernnetworking-api:$modernNetworkingVersion")

    // If you want Bukkit support, you may use this.
    implementation("xyz.bluspring.modernnetworking:modernnetworking-bukkit:$modernNetworkingVersion")

    // Or if you want Velocity support, you may use this.
    implementation("xyz.bluspring.modernnetworking:modernnetworking-velocity:$modernNetworkingVersion")
}

```

### Minecraft Mod Development Setup
If you are on Fabric/Architectury Loom and using Minecraft 1.21.11 and earlier, make sure to switch `implementation` with `modImplementation`.
Alternatively, if you are using MinecraftForge and are on ForgeGradle, make sure to wrap the dependency notation
with `fg.deobf()`.
```kts
dependencies {
    // The mod loader type. Valid types:
    // - fabric
    // - forge
    // - neoforge (only on 1.20.4 and above!)
    val modLoader = "fabric"
    
    // This is the shared library for both modloaders. I recommend adding this for having sources data,
    // even if you do not use a multiloader setup.
    implementation("xyz.bluspring.modernnetworking:modernnetworking-common:$modernNetworkingVersion+$minecraftVersion")
    
    // This is the modloader-specific library. You may bundle this with your mod if you want.
    // Please note that if you bundle this mod, you may create an additional dependency on
    // Fabric Language Kotlin if you are using Fabric, or increase your mod's JAR size due to the
    // Forge/NeoForge-based libraries bundling Kotlin.
    implementation("xyz.bluspring.modernnetworking:modernnetworking-$modLoader:$modernNetworkingVersion+$minecraftVersion")
}
```

| Library Minecraft Version | Supported Minecraft Versions |
|---------------------------|------------------------------|
| 1.18.2                    | 1.18.2 - 1.20.1              |
| 1.20.4                    | 1.20.2 - 1.20.4              |
| 1.20.6                    | 1.20.5 - 1.20.6              |
| 1.21.1                    | 1.21 - Latest                |

## Usage

### Java
```java
// Create your custom packet, like the 1.20.6+ way of doing so.
public record CustomPacket(String data) implements NetworkPacket {
    public static final NetworkCodec<FriendlyByteBuf, CustomPacket> CODEC = CompositeCodecs.composite(
        NetworkCodecs.STRING_UTF8, CustomPacket::data,
        CustomPacket::new
    );
    
    @Override
    public PacketDefinition<? extends NetworkPacket, ? extends ByteBuf> getDefinition() {
        return YourModClass.CUSTOM_PACKET; 
    }
}

// Create the network registry for your namespace.
private static final NamespacedPacketRegistry registry = MinecraftPacketRegistries.CLIENT_PLAY.namespaced("modid");

// Create a clientbound (server -> client) definition for your custom packet.
// This should preferably be a public static final field.
// If desired, you may also specify a definition directly rather than the ID and codec.
public static final PacketDefinition<FriendlyByteBuf, CustomPacket> CUSTOM_PACKET = registry.register("custom_packet", CustomPacket.CODEC);

// Register client network handler for this custom packet.
MinecraftClientPacketHandlers.PLAY.register(CUSTOM_PACKET, (packet, ctx) -> {
    // Handle your logic here.
    // Remember that this is running under the **network thread**,
    // not the client/server thread.
});

// Send packet from server to client.
MinecraftServerPacketHandlers.PLAY.send(serverPlayer, new CustomPacket("Your custom data here"));
```

### Kotlin
```kotlin
// Create your custom packet, like the 1.20.6+ way of doing so.
data class CustomPacket(val data: String) : NetworkPacket {
    override fun getDefinition(): PacketDefinition<out ByteBuf, out NetworkPacket> {
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
private val registry = MinecraftPacketRegistries.CLIENT_PLAY.namespaced("modid")

// Create a clientbound (server -> client) definition for your custom packet.
// This should preferably be a public static final field.
// If desired, you may also specify a definition directly rather than the ID and codec.
val CUSTOM_PACKET = registry.register("custom_packet", CustomPacket.CODEC)

// Register client network handler for this custom packet.
MinecraftClientPacketHandlers.PLAY.register(CUSTOM_PACKET) { packet, ctx ->
    // Handle your logic here.
    // Remember that this is running under the **network thread**,
    // not the client/server thread.
}

// Send packet from server to client.
MinecraftServerPacketHandlers.PLAY.send(serverPlayer, CustomPacket("Your custom data here"))
```
