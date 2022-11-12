package mine.block.bolt.mixin.client;

import mine.block.bolt.brand.BrandingConfig;
import mine.block.bolt.fetchers.BrandingInfoFetcher;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerInfo.class)
public class MixinServerInfo implements BrandingInfoFetcher {
    private BrandingConfig serverData = null;

    @Override
    public void setBrandData(BrandingConfig serverData) {
        this.serverData = serverData;
    }

    @Override
    public BrandingConfig getBrandData() {
        return this.serverData;
    }
}
