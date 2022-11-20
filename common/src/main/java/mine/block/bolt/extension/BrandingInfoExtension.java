package mine.block.bolt.extension;

import mine.block.bolt.brand.BrandingConfig;

public interface BrandingInfoExtension {
    default BrandingConfig getBrandData() {
        throw new AssertionError();
    }

    default void setBrandData(BrandingConfig brandData) {
        throw new AssertionError();
    }
}
