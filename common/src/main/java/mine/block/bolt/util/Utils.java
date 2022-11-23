package mine.block.bolt.util;

import mine.block.bolt.Bolt;
import mine.block.bolt.brand.BrandingConfig;
import mine.block.bolt.brand.SimpleVersionInformation;
import mine.block.bolt.config.BoltConfig;

public class Utils {
    public static boolean comparePingData(BrandingConfig pingData) {
        BrandingConfig config = Bolt.CONFIG.modpackBranding;
        return config.modpackID.equals(pingData.modpackID) && config.modpackName.equals(pingData.modpackName) && config.modpackVersion.ID.equals(pingData.modpackVersion.ID) && config.modpackVersion.semName.equals(pingData.modpackVersion.semName);
    }

    public static boolean compareVersion(SimpleVersionInformation pingData) {
        BrandingConfig config = Bolt.CONFIG.modpackBranding;
        return config.modpackVersion.semName.equals(pingData.semName()) && config.modpackVersion.ID.equals(pingData.ID()) && config.modpackVersion.releaseType.equals(pingData.releaseType()) && config.modpackID.equals(pingData.modpackID()) && config.modpackName.equals(pingData.modpackName());
    }
}
