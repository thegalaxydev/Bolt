package mine.block.bolt.fetchers;

import mine.block.bolt.brand.BrandingConfig;

public interface BrandingInfoFetcher {
    void setBrandData(BrandingConfig brandData);
    BrandingConfig getBrandData();
}
