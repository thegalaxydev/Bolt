package mine.block.bolt.forge.mixin.client;


import net.minecraft.client.network.ServerInfo;
import net.minecraft.server.ServerMetadata;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeHooksClient.class)
public class ForgeClientMixin {
    @Inject(method = "processForgeListPingData", at = @At("HEAD"), cancellable = true)
    private static void bolt$processForgeListPingData(ServerMetadata oldPacket, ServerInfo serverData, CallbackInfo ci) {
        if (oldPacket.getBrandData() != null) {
            serverData.setBrandData(oldPacket.getBrandData());
            ci.cancel();
        }
    }
}
