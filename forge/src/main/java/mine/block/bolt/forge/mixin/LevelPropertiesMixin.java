package mine.block.bolt.forge.mixin;

import com.mojang.serialization.Lifecycle;
import mine.block.bolt.config.BoltConfig;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


// Experimental Warning (Forge)
@Mixin(LevelProperties.class)
public class LevelPropertiesMixin {
    @Inject(method = "hasConfirmedExperimentalWarning", at = @At("HEAD"), cancellable = true, remap = false)
    private void bolt$ignoreExperimentalSettingsScreen(CallbackInfoReturnable<Boolean> cir)
    {
       if(BoltConfig.disableExperimentalWarning.get()) {
           cir.setReturnValue(true);
       }
    }

    @Inject(method = "getLifecycle", at = @At("HEAD"), cancellable = true)
    private void bolt$forceStableLifeCycle(CallbackInfoReturnable<Lifecycle> cir) {
        if(BoltConfig.disableExperimentalWarning.get()) {
            cir.setReturnValue(Lifecycle.stable());
        }
    }
}
