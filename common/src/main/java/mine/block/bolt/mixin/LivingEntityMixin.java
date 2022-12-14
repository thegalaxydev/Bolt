package mine.block.bolt.mixin;

import mine.block.bolt.Bolt;
import mine.block.bolt.config.BoltConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Shadow
    private float movementSpeed;

    @Inject(method = "setMovementSpeed", at = @At(value = "HEAD"), cancellable = true)
    public void bolt$setMovementSpeed(float pSpeed, CallbackInfo ci) {
        if (!Bolt.CONFIG.enableQuickCrouch) return;
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity instanceof PlayerEntity playerEntity) {
            if (playerEntity.isSneaking()) {
                this.movementSpeed = pSpeed * Bolt.CONFIG.quickCrouchSpeed;
                ci.cancel();
            }
        }
    }
}
