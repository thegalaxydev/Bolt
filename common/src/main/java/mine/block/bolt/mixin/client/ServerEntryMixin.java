package mine.block.bolt.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import mine.block.bolt.Bolt;
import mine.block.bolt.brand.BrandingConfig;
import mine.block.bolt.config.BoltConfig;
import mine.block.bolt.util.Utils;
import net.minecraft.client.MinecraftClient;
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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.stream.Collectors;

@Mixin(value = MultiplayerServerListWidget.ServerEntry.class)
public class ServerEntryMixin {
    @Unique
    private boolean firstTime = true;
    @Shadow
    @Final
    private ServerInfo server;

    @Shadow
    @Final
    private MultiplayerScreen screen;

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIIIIIIZF)V", at = @At(value = "HEAD"))
    private void bolt$render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {
        if (!Bolt.CONFIG.modpackBranding.enabled) return;
        int m = mouseX - x;
        int n = mouseY - y;

        ServerInfo data = this.server;
        BrandingConfig pingData = data.getBrandData();
        BrandingConfig localData = Bolt.CONFIG.modpackBranding;
        int idx = 0;
        int idy = 0;
        String tooltip;

        if (this.server.ping < 0 || firstTime) {firstTime=false;return;}
        if (pingData == null) {
            idx = 16;
            idy = 32;
            tooltip = Text.translatable("bolt.gui.tooltip.boltless_server").getString();
        } else if (!pingData.enabled) {
            idy = 32;
            tooltip = Text.translatable("bolt.gui.tooltip.bolt_disabled_server").getString();
        } else if (Utils.comparePingData(pingData)) {
            idx = 16;
            tooltip = Text.translatable("bolt.gui.tooltip.compatible_server", Formatting.GRAY + (pingData.modpackName + " " + pingData.modpackVersion.displayName) + Formatting.RESET, Formatting.GRAY + (localData.modpackName + " " + localData.modpackVersion.displayName) + Formatting.RESET).getString();
        } else {
            idx = 16;
            tooltip = Text.translatable("bolt.gui.tooltip.incompatible_server", (pingData.modpackName + " " + pingData.modpackVersion.displayName), (localData.modpackName + " " + localData.modpackVersion.displayName)).getString();
        }

        tooltip = tooltip + "\n \n" + Formatting.YELLOW + "⚡ Bolt";

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, Bolt.id("textures/gui/bolt-icons.png"));
        DrawableHelper.drawTexture(matrices, x + entryWidth - 18, y + 10, 16, 16, idy, idx, 16, 16, 64, 64);

        int relativeMouseX = mouseX - x;
        int relativeMouseY = mouseY - y;

        if (relativeMouseX > entryWidth - 15 && relativeMouseX < entryWidth && relativeMouseY > 10 && relativeMouseY < 26) {
            this.screen.setTooltip(Arrays.stream(tooltip.split("\n")).map(Text::literal).collect(Collectors.toList()));
        }
    }
}