package mine.block.bolt.forge.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

// Forge - Disable TitleScreen Fade
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Text title) {
        super(title);
    }

//    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"), method = "render")
//    private void init(Args args) {
//
//        TitleScreen.drawStringWithShadow(matrixStack, textRenderer, BoltConfig.modpackBranding.get().modpackName, 2, this.height - 10 - textRenderer.fontHeight, color);
//    } // nope
}
