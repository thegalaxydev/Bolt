package mine.block.bolt.fetchers;

import net.minecraft.nbt.NbtCompound;

public interface EntityDataFetcher {
    default NbtCompound getCustomNbtData() {
        throw new AssertionError();
    }
    default void setCustomNbtData(NbtCompound tag) {
        throw new AssertionError();
    }
}
