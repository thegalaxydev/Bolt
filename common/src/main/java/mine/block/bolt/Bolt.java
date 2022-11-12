package mine.block.bolt;

import mine.block.bolt.config.BoltConfig;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.file.Path;

public class Bolt {
    public static final String MOD_ID = "bolt";

    public static void init(Path configPath) {
        BoltConfig.CONFIG_PATH = configPath.resolve("bolt.config.json");
        try {
            BoltConfig.initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Identifier id(String path) {
        return new Identifier("bolt", path);
    }
}