package mine.block.bolt.world;

import net.minecraft.world.BlockLocating;

import java.util.UUID;

public record FixedPortal(UUID uid, BlockLocating.Rectangle rectangle) {
}