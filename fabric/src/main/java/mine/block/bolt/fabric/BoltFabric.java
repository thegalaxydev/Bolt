package mine.block.bolt.fabric;

import mine.block.bolt.Bolt;
import mine.block.bolt.client.ItemBlinkRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityType;

public class BoltFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Bolt.init(FabricLoader.getInstance().getConfigDir());
    }
}