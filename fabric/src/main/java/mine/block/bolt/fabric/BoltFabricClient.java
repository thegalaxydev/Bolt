package mine.block.bolt.fabric;

import mine.block.bolt.client.ItemBlinkRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.entity.EntityType;

public class BoltFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityType.ITEM, new ItemBlinkRenderer.Factory());
    }
}
