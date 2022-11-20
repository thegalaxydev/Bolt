package mine.block.bolt.util;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybinds {
    public static KeyBinding hideHandKeybind = new KeyBinding(
            "key.bolt.hide_hand",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_F10,
            "category.keybindings.bolt"
    );
}
