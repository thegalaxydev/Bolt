package mine.block.bolt.mixin;

import mine.block.bolt.config.BoltConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    @Shadow
    private float movementSpeed;

    @Inject(method = "setMovementSpeed", at = @At(value = "HEAD"), cancellable = true)
    public void setSpeed(float pSpeed, CallbackInfo ci) {
        if(!BoltConfig.enableQuickCrouch.get()) return;
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        if (livingEntity instanceof PlayerEntity playerEntity) {
            if (playerEntity.isSneaking()) {
                this.movementSpeed = pSpeed * BoltConfig.quickCrouchSpeed.get();
                ci.cancel();
            }
        }
    }
}
