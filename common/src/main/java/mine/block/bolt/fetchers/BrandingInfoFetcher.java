package mine.block.bolt.fetchers;

import mine.block.bolt.brand.BrandingConfig;

public interface BrandingInfoFetcher {
    default void setBrandData(BrandingConfig brandData) {
        throw new AssertionError();
    }
    default BrandingConfig getBrandData() {
        throw new AssertionError();
    }
}
