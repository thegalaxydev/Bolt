package mine.block.bolt.mixin.client;

import mine.block.bolt.Bolt;
import mine.block.bolt.config.BoltConfig;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @ModifyVariable(method = "tickMovement", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"))
    private float bolt$tweakMovement(float f) {
        return Bolt.CONFIG.enableQuickCrouch ? (0.3F + EnchantmentHelper.getSwiftSneakSpeedBoost((ClientPlayerEntity) (Object) this)) * 10.0F : f;
    }
}
