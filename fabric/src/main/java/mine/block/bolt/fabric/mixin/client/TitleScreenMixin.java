package mine.block.bolt.fabric.mixin.client;

import mine.block.bolt.Bolt;
import mine.block.bolt.config.BoltConfig;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

// Fabric - Disable TitleScreen Fade
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    @Mutable
    @Shadow @Final private boolean doBackgroundFade;

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "init()V")
    private void bolt$init(CallbackInfo ci) {
        if(Bolt.CONFIG.skipTitleFadeIn) {
            this.doBackgroundFade = false;
        }
    }

    @ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"), method = "render")
    private void bolt$init(Args args) {
        if (!Bolt.CONFIG.modpackBranding.enableTitlescreenBranding && !Bolt.CONFIG.modpackBranding.enabled) return;
        MatrixStack matrixStack = args.get(0);
        TextRenderer textRenderer = args.get(1);
        String string = args.get(2);
        int x = args.get(3);
        int y = args.get(4);
        int color = args.get(5);

        TitleScreen.drawStringWithShadow(matrixStack, textRenderer, Bolt.CONFIG.modpackBranding.modpackName, 2, this.height - 10 - textRenderer.fontHeight, color);
    }
}
