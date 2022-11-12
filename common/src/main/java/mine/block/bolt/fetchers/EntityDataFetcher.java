package mine.block.bolt.fetchers;

import net.minecraft.nbt.NbtCompound;

public interface EntityDataFetcher {
    NbtCompound getCustomNbtData();
    void setCustomNbtData(NbtCompound tag);
}
