package mine.block.bolt.forge.mixin.client;


import mine.block.bolt.config.BoltConfig;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.server.ServerMetadata;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeHooksClient.class)
public class ForgeClientMixin {
    @Inject(method = "createWorldConfirmationScreen", at = @At("HEAD"), cancellable = true, remap = false)
    private static void bolt$createWorldConfigmationScreen(Runnable doConfirmedWorldLoad, CallbackInfo ci) {
        if(BoltConfig.disableExperimentalWarning.get()) {
            ci.cancel();
            doConfirmedWorldLoad.run();
        }
    }

    @Inject(method = "processForgeListPingData", at = @At("HEAD"), cancellable = true)
    private static void bolt$processForgeListPingData(ServerMetadata oldPacket, ServerInfo serverData, CallbackInfo ci) {
        if (oldPacket.getBrandData() != null) {
            serverData.setBrandData(oldPacket.getBrandData());
            ci.cancel();
        }
    }
}
