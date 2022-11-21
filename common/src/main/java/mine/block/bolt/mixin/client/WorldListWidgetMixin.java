package mine.block.bolt.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import mine.block.bolt.Bolt;
import mine.block.bolt.brand.BrandingConfig;
import mine.block.bolt.brand.SimpleVersionInformation;
import mine.block.bolt.config.BoltConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.BackupPromptScreen;
import net.minecraft.client.gui.screen.world.EditWorldScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static mine.block.bolt.util.Utils.compareVersion;

@Mixin(WorldListWidget.class)
public class WorldListWidgetMixin {
    @Mixin(WorldListWidget.WorldEntry.class) // so the mixin export doesn't freak out
    public abstract static class WorldEntryMixin extends WorldListWidget.Entry {
        @Shadow
        @Final
        private MinecraftClient client;

        @Shadow
        @Final
        private SelectWorldScreen screen;

        @Shadow
        @Final
        private LevelSummary level;
        @Unique
        private boolean isCompatible;

        @Shadow
        protected abstract void start();

        @Inject(method = "play", at = @At("HEAD"), cancellable = true)
        private void bolt$play(CallbackInfo ci) {
            if (!BoltConfig.modpackBranding.get().enabled) return;
            if (this.level.isUnavailable() || this.isCompatible) {
                return;
            }

            SimpleVersionInformation versionInformation = level.getLevelInfo().getVersion();
            if (versionInformation.releaseType().equals("DISABLED")) {
                versionInformation = new SimpleVersionInformation("Minecraft", "minecraft", "mc-" + level.getVersionInfo().getVersion().getId(), level.getVersionInfo().getVersionName(), "DISABLED");
            }
            //BrandingConfig pingData = data.getBrandData();
            BrandingConfig localData = BoltConfig.modpackBranding.get();

            String string2 = "selectWorld.backupQuestion.snapshot";
            MutableText mutableText = Text.translatable(string2);

            MutableText text = Text.translatable("bolt.selectWorld.backupWarning.modpack", versionInformation.modpackName(), versionInformation.semName(), localData.modpackName, localData.modpackVersion.semName);
            this.client.setScreen(new BackupPromptScreen(this.screen, (backup, eraseCache) -> {
                if (backup) {
                    String string = this.level.getName();
                    try (LevelStorage.Session session = this.client.getLevelStorage().createSession(string)) {
                        EditWorldScreen.backupLevel(session);
                    } catch (IOException iOException) {
                        SystemToast.addWorldAccessFailureToast(this.client, string);
                        //LOGGER.error("Failed to backup level {}", (Object)string, (Object)iOException);
                    }
                }
                this.start();
            }, mutableText, text, false));
            ci.cancel();
        }


        @Inject(method = "render", at = @At("RETURN"))
        private void bolt$render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {
            if (!BoltConfig.modpackBranding.get().enabled) return;
//            String text = "Modpack Version: 1.0.0";
//            this.client.textRenderer.draw(matrices, text, x + entryWidth - client.textRenderer.getWidth(text)/*, (float)(x + 32 + 3)*/, (float)(y + 1/*this.client.textRenderer.fontHeight + this.client.textRenderer.fontHeight + 3*/), 0x808080);
            int m = mouseX - x;
            int n = mouseY - y;

//            BrandingInfoFetcher data = (BrandingInfoFetcher) new BrandingInfoFetcher() {
//                @Override
//                public void setBrandData(BrandingConfig brandData) {
//
//                }
//
//                @Override
//                public BrandingConfig getBrandData() {
//                    return ;
//                }
//            };
            SimpleVersionInformation versionInformation = level.getLevelInfo().getVersion();
            //BrandingConfig pingData = data.getBrandData();
            BrandingConfig localData = BoltConfig.modpackBranding.get();
            int idx;
            int idy = 0;
            String tooltip;

            if (versionInformation == null || versionInformation.releaseType().equals("DISABLED")) {
                this.isCompatible = false;
                idx = 32;
                idy = 16;
                tooltip = Text.translatable("bolt.gui.tooltip.non_bolt_world").getString();
            } else if (compareVersion(versionInformation)) {
                idx = 0;
                this.isCompatible = true;
                //tooltip = Text.translatable("bolt.gui.tooltip.compatible_server", Formatting.GRAY + (pingData.modpackName + " " + pingData.modpackVersion.semName) + Formatting.RESET, Formatting.GRAY + (localData.modpackName + " " + localData.modpackVersion.semName) + Formatting.RESET).getString();
                tooltip = Text.translatable("bolt.gui.tooltip.compatible_world", Formatting.GRAY + (versionInformation.modpackName() + " " + versionInformation.semName()) + Formatting.RESET, Formatting.GRAY + (localData.modpackName + " " + localData.modpackVersion.semName) + Formatting.RESET).getString();
            } else  {
                idx = 16;
                this.isCompatible = false;
                //tooltip = Text.translatable("bolt.gui.tooltip.incompatible_server", (pingData.modpackName + " " + pingData.modpackVersion.semName), (localData.modpackName + " " + localData.modpackVersion.semName)).getString();
                tooltip = Text.translatable("bolt.gui.tooltip.incompatible_world", (versionInformation.modpackName() + " " + versionInformation.semName()), (localData.modpackName + " " + localData.modpackVersion.semName)).getString();
            }


            tooltip = tooltip + "\n \n" + Formatting.YELLOW + "⚡ Bolt";

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, Bolt.id("textures/gui/bolt-icons.png"));
            DrawableHelper.drawTexture(matrices, x + entryWidth - 18, y + 10, 16, 16, idx, idy, 16, 16, 64, 64);

            int relativeMouseX = mouseX - x;
            int relativeMouseY = mouseY - y;

            if (relativeMouseX > entryWidth - 15 && relativeMouseX < entryWidth && relativeMouseY > 10 && relativeMouseY < 26) {
                this.screen.setTooltip(Arrays.stream(tooltip.split("\n")).map(Text::literal).map(Text::asOrderedText).collect(Collectors.toList()));
            }
        }
    }
}
