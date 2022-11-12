package mine.block.bolt.forge.mixin.client;

import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


// Experimental Warning (Forge)
@Mixin(LevelProperties.class)
public class MixinLevelProperties {
    @Inject(method = "hasConfirmedExperimentalWarning", at = @At("HEAD"), cancellable = true, remap = false)
    private void ignoreExperimentalSettingsScreen(CallbackInfoReturnable<Boolean> cir)
    {
       cir.setReturnValue(true);
    }
}
