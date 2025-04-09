# Modern Networking
A multiversioned and multiloader network packet mod API based on 1.20.6+'s method of packets.

This API has also been designed in such a way that allows for Bukkit implementations, and even implementations outside
of Minecraft, however you will have to provide the backing implementations yourself.

## Installation (for developers)
TODO

## Usage
```java
// Create your custom packet, like the 1.20.6+ way of doing so.
public record CustomPacket(String data) implements NetworkPacket {
    public static final NetworkCodec<CustomPacket, FriendlyByteBuf> CODEC = CompositeCodecs.composite(
        NetworkCodecs.STRING_UTF8, CustomPacket::data,
        CustomPacket::new
    );
    
    @Override
    public PacketDefinition<? extends NetworkPacket, ? super ByteBuf> getDefinition() {
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

