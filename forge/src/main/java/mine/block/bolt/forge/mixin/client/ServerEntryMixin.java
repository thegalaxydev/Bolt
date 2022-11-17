package mine.block.bolt.forge.mixin.client;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = MultiplayerServerListWidget.ServerEntry.class)
public class ServerEntryMixin {
    @Shadow
    @Final
    private ServerInfo server;

    @Shadow
    @Final
    private MultiplayerScreen screen;

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ForgeHooksClient;drawForgePingInfo(Lnet/minecraft/client/gui/screen/multiplayer/MultiplayerScreen;Lnet/minecraft/client/network/ServerInfo;Lnet/minecraft/client/util/math/MatrixStack;IIIII)V"), remap = false)
    private void bolt$render(MultiplayerScreen gui, ServerInfo target, MatrixStack poseStack, int x, int y, int width, int relativeMouseX, int relativeMouseY) {

    }
}