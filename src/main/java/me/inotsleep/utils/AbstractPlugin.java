package me.inotsleep.utils;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        doEnable();
    }
    
    @Override
    public void onDisable() {
        doDisable();
    }
    
    public abstract void doEnable();
    public abstract void doDisable();
    
    public void printError(String message, boolean severe) {
        if (severe) {
            getLogger().severe(message);
        } else {
            getLogger().warning(message);
        }
    }
    
    public static AbstractPlugin getInstance() {
        // This is a simplified implementation - in the original it might be more complex
        return (AbstractPlugin) org.bukkit.Bukkit.getPluginManager().getPlugin("MultiVersionResourcePacks");
    }
}