package mine.block.bolt.extension;

import mine.block.bolt.brand.BrandingConfig;
import mine.block.bolt.brand.SimpleVersionInformation;

public interface SimpleBrandingVersionExtension {
    default public SimpleVersionInformation getVersion() {
        throw new AssertionError();
    }

    default public void setVersion(SimpleVersionInformation version) {
        throw new AssertionError();
    }
}
