package mine.block.bolt.client;

import mine.block.bolt.Bolt;
import mine.block.bolt.config.BoltConfig;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;

public class ItemBlinkRenderer extends ItemEntityRenderer {
    public ItemBlinkRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(ItemEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight) {
        int remainingTime = Bolt.CONFIG.despawnBlinkSpeed - entity.getItemAge();
        if (remainingTime <= 20 * Bolt.CONFIG.despawnBlinkStartTime) {
            if (Bolt.CONFIG.enableItemDespawnBlink) {
                int flashFactor = Math.max(2, remainingTime / 20);
                if (remainingTime % flashFactor < 0.5f * flashFactor) {
                    return;
                }
            } else if (remainingTime % 20 < 10) {
                return;
            }
        }
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    public static class Factory implements EntityRendererFactory<ItemEntity> {
        @Override
        public EntityRenderer<ItemEntity> create(Context context) {
            return new ItemBlinkRenderer(context);
        }
    }
}