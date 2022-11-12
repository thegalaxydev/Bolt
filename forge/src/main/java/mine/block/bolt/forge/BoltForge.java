package mine.block.bolt.forge;

import mine.block.bolt.Bolt;
import mine.block.bolt.client.ItemBlinkRenderer;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Bolt.MOD_ID)
public class BoltForge {
    public BoltForge() {
        Bolt.init(FMLLoader.getGamePath().resolve("/config"));
    }

    @Mod.EventBusSubscriber(modid = Bolt.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Events {

        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityType.ITEM, new ItemBlinkRenderer.Factory());
        }

    }
}