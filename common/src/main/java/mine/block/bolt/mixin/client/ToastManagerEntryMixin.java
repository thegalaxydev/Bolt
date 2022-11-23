package mine.block.bolt.mixin.client;

import mine.block.bolt.Bolt;
import mine.block.bolt.config.BoltConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Cross Platform - Disable Toast Fade
@Mixin(targets = "net.minecraft.client.toast.ToastManager$Entry")
public class ToastManagerEntryMixin {
    @Inject(method = "getDisappearProgress", at = @At("HEAD"), cancellable = true)
    public void bolt$getVisibility(CallbackInfoReturnable<Long> cir) {
        if (Bolt.CONFIG.skipToastFadeOut) {
            cir.setReturnValue((long) 1.0);
        }
    }
}
