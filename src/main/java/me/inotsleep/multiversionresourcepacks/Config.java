package me.inotsleep.multiversionresourcepacks;

import me.inotsleep.utils.AbstractPlugin;
import me.inotsleep.utils.config.AbstractConfig;
import me.inotsleep.utils.config.Path;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Config extends AbstractConfig {
    File packFolder;

    @Path(path = "packs")
    public Map<String, Pack> resourcePackMap;

    @Path(path="settings.port")
    public int fileHostPort = 3000;

    @Path(path="settings.bind-ip")
    public String fileHostBindIP = "0.0.0.0";

    @Path(path="settings.public-link")
    public String fileHostPublic = "http://localhost:3000/";

    @Path(path = "settings.httpHandlerThreads")
    public int threads = 10;

    public Config(AbstractPlugin plugin) {
        super(plugin, "config.yml");
        packFolder = new File(plugin.getDataFolder(), "packs");
    }

    @Override
    public String getHeader() {
        return "You can define 2 types of packs:" + "\n"+
                "1. External pack: isUrl: true, url: url to pack, hash: hash of pack" +"\n"+
                "2. Internal pack: isUrl: false, fileName: name of pack" +"\n"+
                "\n"+
                "Internal packs must be in plugins/MultiVersionResourcePacks/packs folder." +"\n"+
                "\n"+
                "Protocols:" +"\n"+
                "To specify protocol version, you can use >=, <=, >, < and ==" +"\n"+
                "Recommended to use only >=, > and ==, because protocols sorted from lowest to highest" +"\n"+
                "If >, <, >=, <= finds any pack, it counties finding pack for next protocols." +"\n"+
                "If == finds any pack, it will stop finding and send player this pack" +"\n"+
                "\n"+
                "Latest Minecraft versions and their protocols:" +"\n"+
                "1.21.5: 768, 1.21.4: 768, 1.21.3: 768, 1.21.2: 768" +"\n"+
                "1.21.1: 767, 1.21.0: 767" +"\n"+
                "1.20.6: 766, 1.20.5: 766, 1.20.4: 765, 1.20.3: 765" +"\n"+
                "1.20.2: 764, 1.20.1: 763, 1.20.0: 763" +"\n"+
                "\n"+
                "In settings.public-link you need to specify pack host external ip," +"\n"+
                "because plugin cannot know yours machine ip. Also it can be dangerous to show your ip to players," +"\n"+
                "so you can connect nginx, cloudflare, etc." +  "\n" +
                "\n" +
                "If you want specify by version, not by protocol number,\n" +
                "you need to use full version string.\n" +
                "You can find them here: https://github.com/ViaVersion/ViaVersion/blob/master/api/src/main/java/com/viaversion/viaversion/api/protocol/version/ProtocolVersion.java#L45\n" +
                "Use value, that specified in register method, not field name.";
        
    }

    @Override
    public void saveDefaults() {
        if (!packFolder.exists()) {
            packFolder.mkdir();
        }

        resourcePackMap = new HashMap<>();

        // Examples for different version ranges
        
        // Latest versions (1.21.5 and above) - protocol 768+
        resourcePackMap.put(">=1_21_5", new Pack(null, "example_hash_1215", "https://example.com/pack_1_21_5.zip", true));
        
        // 1.21.x versions - protocol 767-768
        resourcePackMap.put(">=1_21", new Pack(null, "example_hash_121", "https://example.com/pack_1_21.zip", true));
        
        // 1.20.x versions - protocol 763-766  
        resourcePackMap.put(">=1_20", new Pack(null, "example_hash_120", "https://example.com/pack_1_20.zip", true));
        
        // 1.19.x versions - protocol 759-762
        resourcePackMap.put(">=1_19", new Pack(null, "example_hash_119", "https://example.com/pack_1_19.zip", true));
        
        // 1.16.x versions using protocol numbers
        resourcePackMap.put(">=753", new Pack("myPack_1_16.zip", null, null, false));
        
        // Older versions (1.12.2 and below) - legacy support
        resourcePackMap.put("<=1_12_2", new Pack(null, "8c96d8084fa706661d0a7cf9b084bef4161d520b", "https://mediafilez.forgecdn.net/files/5505/931/Faithful%2032x%20-%201.16.5.zip", true));
        
        saveDefaultsToConfig();
    }
}
