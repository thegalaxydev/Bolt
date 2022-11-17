package mine.block.bolt.forge.mixin.client;

import net.minecraft.client.gui.screen.world.WorldListWidget;

import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Experimental Warning (Forge)
@Mixin(WorldListWidget.WorldEntry.class)
public class WorldSelectionListEntryMixin {
    @Inject(method = "renderExperimentalWarning", at = @At("HEAD"), cancellable = true, remap = false)
    private void bolt$ignoreExperimentalWarningIcon(MatrixStack stack, int mouseX, int mouseY, int top, int left, CallbackInfo ci)
    {
       ci.cancel();
    }
}
