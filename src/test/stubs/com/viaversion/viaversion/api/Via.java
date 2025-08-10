package com.viaversion.viaversion.api;

import org.bukkit.entity.Player;

public class Via {
    public static ViaAPI<Player> getAPI() {
        return new ViaAPI<Player>() {
            @Override
            public int getPlayerVersion(Player player) {
                return 768; // Default to 1.21.5 protocol
            }
        };
    }
}