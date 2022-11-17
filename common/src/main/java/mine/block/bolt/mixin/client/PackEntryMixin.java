package mine.block.bolt.mixin.client;

import mine.block.bolt.config.BoltConfig;
import net.minecraft.client.gui.screen.pack.PackListWidget;
import net.minecraft.resource.ResourcePackCompatibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Cross Platform - Disable Resource Pack Warning
@Mixin(PackListWidget.ResourcePackEntry.class)
public class PackEntryMixin {
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/resource/ResourcePackCompatibility;isCompatible()Z"
            )
    )
    private boolean bolt$onRenderRedirectIsCompatible(ResourcePackCompatibility compatibility) {
        return BoltConfig.disableResourcePackWarning.get() || compatibility.isCompatible();
    }

    @Redirect(
            method = "mouseClicked",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/resource/ResourcePackCompatibility;isCompatible()Z"
            )
    )
    private boolean bolt$onMouseClickedRedirectIsCompatible(ResourcePackCompatibility compatibility) {
        return BoltConfig.disableResourcePackWarning.get() || compatibility.isCompatible();
    }
}
