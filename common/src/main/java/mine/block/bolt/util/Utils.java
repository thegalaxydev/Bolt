package mine.block.bolt.util;

import mine.block.bolt.brand.BrandingConfig;
import mine.block.bolt.config.BoltConfig;

public class Utils {
    public static boolean comparePingData(BrandingConfig pingData) {
        BrandingConfig config = BoltConfig.modpackBranding.get();
        return config.modpackID.equals(pingData.modpackID) && config.modpackName.equals(pingData.modpackName) && config.modpackVersion.ID.equals(pingData.modpackVersion.ID) && config.modpackVersion.semName.equals(pingData.modpackVersion.semName);
    }

    public static boolean compareVersion(BrandingConfig.VersionInformation pingData) {
        BrandingConfig config = BoltConfig.modpackBranding.get();
        return config.modpackVersion.semName.equals(pingData.semName) && config.modpackVersion.ID.equals(pingData.ID) && config.modpackVersion.releaseType.equals(pingData.releaseType);
    }
}
