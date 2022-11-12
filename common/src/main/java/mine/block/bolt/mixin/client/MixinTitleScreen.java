package mine.block.bolt.mixin.client;

import mine.block.bolt.config.BoltConfig;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Cross Platform - Disable TitleScreen Fade
@Mixin(TitleScreen.class)
public class MixinTitleScreen {
    @Mutable
    @Shadow @Final private boolean doBackgroundFade;

    @Inject(at = @At("TAIL"), method = "init()V")
    private void init(CallbackInfo ci) {
        if(BoltConfig.skipTitleFadeIn.get()) {
            this.doBackgroundFade = false;
        }
    }
}
