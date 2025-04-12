package net.fullstackjones.fullstackeconomy.networking;

import com.mojang.serialization.Codec;

import java.util.UUID;

public class UUIDCodec {
    public static final Codec<UUID> CODEC = Codec.STRING.xmap(
            UUID::fromString, // Deserialize: Convert string to UUID
            UUID::toString    // Serialize: Convert UUID to string
    );
}
