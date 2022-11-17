package mine.block.bolt.extension;

import mine.block.bolt.brand.BrandingConfig;

public interface BrandingInfoExtension {
    default void setBrandData(BrandingConfig brandData) {
        throw new AssertionError();
    }
    default BrandingConfig getBrandData() {
        throw new AssertionError();
    }
}
