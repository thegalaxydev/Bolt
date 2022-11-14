package mine.block.bolt.forge.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import mine.block.bolt.Bolt;
import mine.block.bolt.brand.BrandingConfig;
import mine.block.bolt.config.BoltConfig;
import mine.block.bolt.util.Utils;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.stream.Collectors;

@Mixin(value = MultiplayerServerListWidget.ServerEntry.class)
public class MixinServerEntry {
    @Shadow
    @Final
    private ServerInfo server;

    @Shadow
    @Final
    private MultiplayerScreen screen;

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ForgeHooksClient;drawForgePingInfo(Lnet/minecraft/client/gui/screen/multiplayer/MultiplayerScreen;Lnet/minecraft/client/network/ServerInfo;Lnet/minecraft/client/util/math/MatrixStack;IIIII)V"), remap = false)
    private void render(MultiplayerScreen gui, ServerInfo target, MatrixStack poseStack, int x, int y, int width, int relativeMouseX, int relativeMouseY) {

    }
}