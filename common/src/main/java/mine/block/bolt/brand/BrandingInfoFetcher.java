package mine.block.bolt.brand;

public interface BrandingInfoFetcher {
    void setBrandData(BrandingConfig serverData);
    BrandingConfig getBrandData();
}
