package mine.block.bolt.forge;

import mine.block.bolt.Bolt;
import mine.block.bolt.Constants;
import mine.block.bolt.client.ItemBlinkRenderer;
import mine.block.bolt.config.BoltConfig;
import mine.block.bolt.util.Keybinds;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(Constants.MOD_ID)
public class BoltForge {
    public BoltForge() {
        Bolt.init(FMLPaths.CONFIGDIR.get());
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Events {
        public Events() {
            MinecraftForge.EVENT_BUS.addListener(Events::screenInit);
        }

        public static void screenInit(ScreenEvent.Init.Post event) {
            if (BoltConfig.modpackBranding.get().enableTitlescreenBranding) {
                try {
                    if (event.getScreen() instanceof TitleScreen titleScreen) {
                        titleScreen.doBackgroundFade = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @SubscribeEvent
        public static void clientTick(TickEvent.ClientTickEvent tickEvent) {
            if(!Keybinds.hideHandKeybind.isPressed()) {
                Constants.canHideHand = false;
            }
            while (Keybinds.hideHandKeybind.wasPressed()) {
                Constants.canHideHand = true;
            }
        }

        @SubscribeEvent
        public static void registeKeys(RegisterKeyMappingsEvent event) {
            event.register(Keybinds.hideHandKeybind);
        }

        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityType.ITEM, new ItemBlinkRenderer.Factory());
        }

    }
}