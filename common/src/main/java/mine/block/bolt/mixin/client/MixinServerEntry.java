package mine.block.bolt.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import mine.block.bolt.Bolt;
import mine.block.bolt.brand.BrandingInfoFetcher;
import mine.block.bolt.config.BoltConfig;
import mine.block.bolt.brand.BrandingConfig;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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

        @Unique
        private static boolean comparePingData(BrandingConfig pingData) {
            BrandingConfig config = BoltConfig.modpackBranding.get();
            return config.modpackID.equals(pingData.modpackID) && config.modpackName.equals(pingData.modpackName) && config.modpackVersion.ID.equals(pingData.modpackVersion.ID);
        }

        @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIIIIIIZF)V", at = @At(value = "HEAD"))
        private void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {
            int m = mouseX - x;
            int n = mouseY - y;

            BrandingInfoFetcher data = (BrandingInfoFetcher) this.server;
            BrandingConfig pingData = data.getBrandData();
            BrandingConfig localData = BoltConfig.modpackBranding.get();
            int idx;
            String tooltip;

            if (pingData == null) {
                return;
            }

            if (comparePingData(pingData)) {
                idx = 0;
                tooltip = Text.translatable("bolt.gui.tooltip.compatible_server", Formatting.GRAY + (pingData.modpackName + " " + pingData.modpackVersion.semName) + Formatting.RESET, Formatting.GRAY + (localData.modpackName + " " + localData.modpackVersion.semName) + Formatting.RESET).getString();
            } else {
                idx = 16;
                tooltip = Text.translatable("bolt.gui.tooltip.incompatible_server", (pingData.modpackName + " " + pingData.modpackVersion.semName), (localData.modpackName + " " + localData.modpackVersion.semName)).getString();
            }

            tooltip = tooltip + "\n \n" + Formatting.YELLOW + "⚡ Bolt";

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, Bolt.id("textures/gui/compat-icons.png"));
            DrawableHelper.drawTexture(matrices, x + entryWidth - 18, y + 10, 16, 16, 0, idx, 16, 16, 16, 32);

            int relativeMouseX = mouseX - x;
            int relativeMouseY = mouseY - y;

            if (relativeMouseX > entryWidth - 15 && relativeMouseX < entryWidth && relativeMouseY > 10 && relativeMouseY < 26) {
                this.screen.setTooltip(Arrays.stream(tooltip.split("\n")).map(Text::literal).collect(Collectors.toList()));
            }
        }
}