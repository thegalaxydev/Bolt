package mine.block.bolt.forge;

import mine.block.bolt.Bolt;
import mine.block.bolt.client.ItemBlinkRenderer;
import mine.block.bolt.config.BoltConfig;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.gui.ScreenUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

@Mod(Bolt.MOD_ID)
public class BoltForge {
    public BoltForge() {
        Bolt.init(FMLPaths.CONFIGDIR.get());
    }

    @Mod.EventBusSubscriber(modid = Bolt.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Events {
        public Events() {
            MinecraftForge.EVENT_BUS.addListener(Events::screenInit);
        }

        public static void screenInit(ScreenEvent.Init.Post event) {
            if (BoltConfig.modpackBranding.get().titleScreenBrander)
            try {
                if (event.getScreen() instanceof TitleScreen titleScreen) {
                    titleScreen.doBackgroundFade = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityType.ITEM, new ItemBlinkRenderer.Factory());
        }

    }
}