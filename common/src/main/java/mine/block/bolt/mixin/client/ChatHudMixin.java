package mine.block.bolt.mixin.client;

import mine.block.bolt.config.BoltConfig;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Cross Platform - Chat History & Chat Clearing Tweaks
@Mixin(ChatHud.class)
public class ChatHudMixin {
    @ModifyConstant(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", constant = @Constant(intValue = 100), expect = 2)
    public int bolt$increaseMaxHistory(int original) {
        if (BoltConfig.disableChatClearing.get()) {
            return 16384;
        } else {
            return original;
        }
    }

    @Inject(method = "clear", at = @At("HEAD"), cancellable = true)
    public void bolt$clear(boolean bl, CallbackInfo ci) {
        if (!BoltConfig.clearChatHistory.get()) {
            ci.cancel();
        }
    }
}
