package mine.block.bolt.mixin.client;

import mine.block.bolt.brand.BrandingConfig;
import mine.block.bolt.extension.BrandingInfoExtension;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerInfo.class)
public class ServerInfoMixin implements BrandingInfoExtension {
    private BrandingConfig serverData = null;

    @Override
    public BrandingConfig getBrandData() {
        return this.serverData;
    }

    @Override
    public void setBrandData(BrandingConfig serverData) {
        this.serverData = serverData;
    }
}
