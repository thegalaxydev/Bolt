package mine.block.bolt;

import mine.block.bolt.config.BoltConfig;
import net.minecraft.util.Identifier;

import java.nio.file.Path;

public class Bolt {

    public static void init(Path configPath) {
        //GameMenuScreen
        BoltConfig.CONFIG_PATH = configPath.resolve("bolt.config.json");
        try {
            BoltConfig.initialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //WorldListWidget
    }

    public static Identifier id(String path) {
        return new Identifier("bolt", path);
    }
}