package mine.block.bolt.forge.mixin.client;

import org.spongepowered.asm.mixin.Mixin;

// Forge - Server Compatibility Checker
@Mixin(targets = "net/minecraft/client/network/MultiplayerServerListPinger$1", remap = false)
public class ServerListPingerMixin {

//    @Shadow @Final private ServerInfo field_3776;
//
//    @Inject(method = "onResponse(Lnet/minecraft/network/packet/s2c/query/QueryResponseS2CPacket;)V", at = @At("HEAD"))
//        public void onResponse(QueryResponseS2CPacket packet, CallbackInfo ci) {
//            BrandingInfoFetcher serverInfo = this.field_3776;
//            serverInfo.setBrandData(packet.getServerMetadata().getBrandData());
//        }
}
