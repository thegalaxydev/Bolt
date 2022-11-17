package mine.block.bolt.extension;

import net.minecraft.nbt.NbtCompound;

public interface EntityDataExtension {
    default NbtCompound getCustomNbtData() {
        throw new AssertionError();
    }
    default void setCustomNbtData(NbtCompound tag) {
        throw new AssertionError();
    }
}
