package mine.block.bolt.mixin.client;

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
public class MixinSplashOverlay {
    @Mutable
    @Shadow @Final
    private MinecraftClient client;
    @Shadow
    private long reloadCompleteTime;

    @Inject(method = "render", at = @At("HEAD"))
    public void injectRenderHead(CallbackInfo ci) {
        if(BoltConfig.skipLoadingTransition.get()) {
            if (this.reloadCompleteTime > 1) {
                this.client.setOverlay(null);
            }
        }
    }

    @ModifyVariable(method = "withAlpha", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private static int modifyWithAlpha(int value) {
        if(BoltConfig.skipLoadingTransition.get()) {
            return 255;
        } else return value;
    }

    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 3)
    private float modifyOvar(float value) {
        if(BoltConfig.skipLoadingTransition.get()) {
            return 1.0f;
        } else return value;
    }
}
