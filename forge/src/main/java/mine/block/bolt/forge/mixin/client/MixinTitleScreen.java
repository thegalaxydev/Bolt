package mine.block.bolt.forge.mixin.client;

import mine.block.bolt.config.BoltConfig;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraftforge.internal.BrandingControl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

// Cross Platform - Disable TitleScreen Fade
@Mixin(TitleScreen.class)
public abstract class MixinTitleScreen extends Screen {
    @Mutable
    @Shadow @Final private boolean doBackgroundFade;

    protected MixinTitleScreen(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "<init>(Z)V")
    private void init(CallbackInfo ci) {
        if(BoltConfig.skipTitleFadeIn.get()) {
            this.doBackgroundFade = false;
        }
    }

//    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"), method = "render")
//    private void init(Args args) {
//
//        TitleScreen.drawStringWithShadow(matrixStack, textRenderer, BoltConfig.modpackBranding.get().modpackName, 2, this.height - 10 - textRenderer.fontHeight, color);
//    } // nope
}
