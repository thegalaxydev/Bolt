package mine.block.bolt.fabric.mixin.client;

import mine.block.bolt.extension.BrandingInfoExtension;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.packet.s2c.query.QueryResponseS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Fabric - Server Compatibility Checker
@Mixin(targets = "net/minecraft/client/network/MultiplayerServerListPinger$1")
public class ServerListPingerMixin {
        @Shadow
        @Final
        ServerInfo field_3776;

        @Inject(method = "onResponse(Lnet/minecraft/network/packet/s2c/query/QueryResponseS2CPacket;)V", at = @At("HEAD"))
        public void bolt$onResponse(QueryResponseS2CPacket packet, CallbackInfo ci) {
            BrandingInfoExtension serverInfo = this.field_3776;
            serverInfo.setBrandData(packet.getServerMetadata().getBrandData());
        }
}
