package mine.block.bolt.mixin.client;

import mine.block.bolt.config.BoltConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Cross Platform - Disable Toast Fade
@Mixin(targets = "net.minecraft.client.toast.ToastManager$Entry")
public class MixinToastManager$Entry {
    @Inject(method = "getDisappearProgress", at = @At("HEAD"), cancellable = true)
    public void getVisibility(CallbackInfoReturnable<Long> cir) {
        if(BoltConfig.skipToastFadeOut.get()) {
            cir.setReturnValue((long)1.0);
        }
    }
}
