package mine.block.bolt.mixin.client;

import mine.block.bolt.Bolt;
import mine.block.bolt.config.BoltConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashOverlay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Cross Platform - Disable SplashOverlay Fade
@Mixin(SplashOverlay.class)
public class SplashOverlayMixin {
    @Mutable
    @Shadow
    @Final
    private MinecraftClient client;
    @Shadow
    private long reloadCompleteTime;

    @ModifyVariable(method = "withAlpha", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private static int bolt$modifyWithAlpha(int value) {
        if (Bolt.CONFIG.skipLoadingTransition) {
            return 255;
        } else return value;
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void bolt$injectRenderHead(CallbackInfo ci) {
        if (Bolt.CONFIG.skipLoadingTransition) {
            if (this.reloadCompleteTime > 1) {
                this.client.setOverlay(null);
            }
        }
    }

    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 3)
    private float bolt$skipLoadingTransition(float value) {
        if (Bolt.CONFIG.skipLoadingTransition) {
            return 1.0f;
        } else return value;
    }
}
