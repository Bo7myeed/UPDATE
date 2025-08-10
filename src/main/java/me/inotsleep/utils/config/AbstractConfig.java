package me.inotsleep.utils.config;

import me.inotsleep.utils.AbstractPlugin;
import me.inotsleep.multiversionresourcepacks.Pack;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractConfig {
    private final AbstractPlugin plugin;
    private final String fileName;
    private File configFile;
    private FileConfiguration config;
    
    public AbstractConfig(AbstractPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.configFile = new File(plugin.getDataFolder(), fileName);
        
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        
        if (!configFile.exists()) {
            saveDefaults();
            save();
        }
        
        load();
    }
    
    public void reload() {
        load();
        loadFields();
    }
    
    private void load() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }
    
    private void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save config file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadFields() {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Path.class)) {
                field.setAccessible(true);
                Path path = field.getAnnotation(Path.class);
                String configPath = path.path();
                
                try {
                    if (field.getType() == Map.class && field.getName().equals("resourcePackMap")) {
                        // Special handling for resourcePackMap
                        Map<String, Pack> packMap = new HashMap<>();
                        ConfigurationSection packsSection = config.getConfigurationSection(configPath);
                        if (packsSection != null) {
                            for (String key : packsSection.getKeys(false)) {
                                ConfigurationSection packSection = packsSection.getConfigurationSection(key);
                                if (packSection != null) {
                                    Pack pack = Pack.deserialize(packSection);
                                    packMap.put(key, pack);
                                }
                            }
                        }
                        field.set(this, packMap);
                    } else {
                        // Standard field loading
                        Object value = config.get(configPath);
                        if (value != null) {
                            field.set(this, value);
                        }
                    }
                } catch (IllegalAccessException e) {
                    plugin.getLogger().warning("Could not load config field " + field.getName() + ": " + e.getMessage());
                }
            }
        }
    }
    
    public abstract String getHeader();
    public abstract void saveDefaults();
    
    @SuppressWarnings("unchecked")
    protected void saveDefaultsToConfig() {
        config.options().header(getHeader());
        
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Path.class)) {
                field.setAccessible(true);
                Path path = field.getAnnotation(Path.class);
                String configPath = path.path();
                
                try {
                    Object value = field.get(this);
                    if (value instanceof Map && field.getName().equals("resourcePackMap")) {
                        // Special handling for resourcePackMap
                        Map<String, Pack> packMap = (Map<String, Pack>) value;
                        ConfigurationSection packsSection = config.createSection(configPath);
                        for (Map.Entry<String, Pack> entry : packMap.entrySet()) {
                            ConfigurationSection packSection = entry.getValue().serialize();
                            packsSection.set(entry.getKey(), packSection);
                        }
                    } else {
                        config.set(configPath, value);
                    }
                } catch (IllegalAccessException e) {
                    plugin.getLogger().warning("Could not save default for field " + field.getName() + ": " + e.getMessage());
                }
            }
        }
    }
}