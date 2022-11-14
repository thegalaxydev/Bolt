package mine.block.bolt.extension;

import mine.block.bolt.brand.BrandingConfig;

public interface SimpleBrandingVersionExtension {
    default public BrandingConfig.VersionInformation getVersion() {
        throw new AssertionError();
    }

    default public void setVersion(BrandingConfig.VersionInformation version) {
        throw new AssertionError();
    }
}
