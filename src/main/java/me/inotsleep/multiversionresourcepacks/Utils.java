package me.inotsleep.multiversionresourcepacks;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Objects;

public class Utils {
    public static String calcSHA1(File file) throws IOException, NoSuchAlgorithmException {

        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        try (InputStream input = new FileInputStream(file)) {

            byte[] buffer = new byte[8192];
            int len = input.read(buffer);

            while (len != -1) {
                sha1.update(buffer, 0, len);
                len = input.read(buffer);
            }

            return byteArray2Hex(sha1.digest());
        }
    }

    public static String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public static int getProtocolFromVersionString(String versionString) {
        versionString = versionString.replace("_", ".");
        
        // Handle common version strings first for better compatibility
        switch (versionString) {
            case "1.21.5": return 768;
            case "1.21.4": return 768;
            case "1.21.3": return 768;
            case "1.21.2": return 768;
            case "1.21.1": return 767;
            case "1.21": return 767;
            case "1.20.6": return 766;
            case "1.20.5": return 766;
            case "1.20.4": return 765;
            case "1.20.3": return 765;
            case "1.20.2": return 764;
            case "1.20.1": return 763;
            case "1.20": return 763;
            case "1.19.4": return 762;
            case "1.19.3": return 761;
            case "1.19.2": return 760;
            case "1.19.1": return 760;
            case "1.19": return 759;
            case "1.18.2": return 758;
            case "1.18.1": return 757;
            case "1.18": return 757;
            case "1.17.1": return 756;
            case "1.17": return 755;
            case "1.16.5": return 754;
            case "1.16.4": return 754;
            case "1.16.3": return 753;
            case "1.16.2": return 751;
            case "1.16.1": return 736;
            case "1.16": return 735;
            case "1.15.2": return 578;
            case "1.15.1": return 575;
            case "1.15": return 573;
        }
        
        // Try ViaVersion protocol lookup as fallback
        try {
            final ProtocolVersion[] version = {null};
            String finalVersionString = versionString;
            ProtocolVersion.getProtocols().forEach(i -> version[0] = Objects.equals(i.getName(), finalVersionString) ? i : version[0]);
            if (version[0] != null) {
                return version[0].getVersion();
            }
        } catch (Exception e) {
            // If ViaVersion lookup fails, continue with fallback logic
        }
        
        return -1;
    }
}
