package mine.block.bolt;

import mine.block.bolt.config.BoltConfig;
import mine.block.bolt.util.PlatformSpecifics;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.file.Path;

public class Bolt {
    public static final String MOD_ID = "bolt";

    public static void init() {
        //GameMenuScreen
        BoltConfig.CONFIG_PATH = PlatformSpecifics.getConfigDir().resolve("bolt.config.json");
        try {
            BoltConfig.initialize();
            BoltConfig.modpackBranding.get().updateProvidedByModpack();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //WorldListWidget
    }

    public static Identifier id(String path) {
        return new Identifier("bolt", path);
    }
}