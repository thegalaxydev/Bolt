package mine.block.bolt.mixin.client;

import mine.block.bolt.Bolt;
import mine.block.bolt.config.BoltConfig;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.toast.Toast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Cross Platform - Disable Toast Fade Sound
@Mixin(Toast.Visibility.class)
public class ToastVisibilityMixin {
    @Inject(method = "playSound", at = @At("HEAD"), cancellable = true)
    public void bolt$playSound(SoundManager soundManager, CallbackInfo ci) {
        if (Bolt.CONFIG.skipToastFadeOut) {
            ci.cancel();
        }
    }
}
