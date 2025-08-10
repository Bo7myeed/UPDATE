package com.viaversion.viaversion.api.protocol.version;

import java.util.List;
import java.util.ArrayList;

public class ProtocolVersion {
    private String name;
    private int version;
    
    public ProtocolVersion(int version, String name) {
        this.version = version;
        this.name = name;
    }
    
    public String getName() { return name; }
    public int getVersion() { return version; }
    
    public static List<ProtocolVersion> getProtocols() {
        List<ProtocolVersion> protocols = new ArrayList<>();
        protocols.add(new ProtocolVersion(768, "1.21.5"));
        protocols.add(new ProtocolVersion(768, "1.21.4"));
        protocols.add(new ProtocolVersion(767, "1.21.1"));
        protocols.add(new ProtocolVersion(767, "1.21"));
        protocols.add(new ProtocolVersion(766, "1.20.6"));
        protocols.add(new ProtocolVersion(763, "1.20.1"));
        return protocols;
    }
    
    public static ProtocolVersion getProtocol(int protocol) {
        return new ProtocolVersion(protocol, "unknown");
    }
}