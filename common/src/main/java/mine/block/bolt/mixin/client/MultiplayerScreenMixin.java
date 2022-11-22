package mine.block.bolt.mixin.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MultiplayerScreen.class)
public abstract class MultiplayerScreenMixin {

    @Inject(method = "init", at = @At("RETURN"))
    private void bolt$init(CallbackInfo ci) {
        ButtonWidget x =  null;
       List<Drawable> drawables = ((ScreenAccessor)this).getDrawables();
        for (Drawable drawable : drawables) {
            if (drawable instanceof ButtonWidget buttonWidget && drawable instanceof ClickableWidgetAccessor accessor) {
                if (accessor.getMessage().getContent() instanceof TranslatableTextContent content) {
                    if (content.getKey().equals("selectServer.select")) {
                        accessor.setWidth(76);
                        // 100 - 70 = 30 - 5 (padding) * 2 = 20

                        x = new ButtonWidget(buttonWidget.x + buttonWidget.getWidth() + 4, buttonWidget.y, 20, 20, Text.literal("F"), a -> {});
                        break;
                    }
                }
            }
        }
        drawables.add(x);
    }
}
