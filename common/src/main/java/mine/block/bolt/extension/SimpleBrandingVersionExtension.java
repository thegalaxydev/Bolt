package mine.block.bolt.extension;

import mine.block.bolt.brand.BrandingConfig;

public interface SimpleBrandingVersionExtension {
    public BrandingConfig.VersionInformation getVersion();

    public void setVersion(BrandingConfig.VersionInformation version);
}
