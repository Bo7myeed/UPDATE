package com.viaversion.viaversion.api;

import org.bukkit.entity.Player;

public interface ViaAPI<T> {
    int getPlayerVersion(T player);
}