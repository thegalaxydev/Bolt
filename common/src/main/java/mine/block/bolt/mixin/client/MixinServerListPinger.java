package mine.block.bolt.mixin.client;

import mine.block.bolt.fetchers.BrandingInfoFetcher;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.packet.s2c.query.QueryResponseS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Cross Platform - Server Compatibility Checker
@Mixin(targets = "net/minecraft/client/network/MultiplayerServerListPinger$1")
public class MixinServerListPinger {
        @Shadow
        @Final
        ServerInfo field_3776;

        @Inject(method = "onResponse(Lnet/minecraft/network/packet/s2c/query/QueryResponseS2CPacket;)V", at = @At("HEAD"))
        public void onResponse(QueryResponseS2CPacket packet, CallbackInfo ci) {
            if (packet.getServerMetadata() instanceof BrandingInfoFetcher brandInfo) {
                BrandingInfoFetcher serverInfo = (BrandingInfoFetcher) this.field_3776;
                serverInfo.setBrandData(brandInfo.getBrandData());
            }
        }
}
