package mine.block.bolt;

import dev.architectury.injectables.targets.ArchitecturyTarget;
import mine.block.bolt.config.BoltConfig;
import mine.block.bolt.util.PlatformSpecifics;
import net.minecraft.util.Identifier;

public class Bolt {
    public static final String MOD_ID = "bolt";
    public static BoltConfig CONFIG = null;

    public static void init() {
        //GameMenuScreen
        BoltConfig.OLD_CONFIG_PATH = PlatformSpecifics.getConfigDir().resolve("bolt.config.json");
        BoltConfig.CONFIG_PATH = PlatformSpecifics.getConfigDir().resolve("bolt.config.json5");
        try {
            BoltConfig.initialize();
            if (CONFIG.modpackBranding.enabled && !ArchitecturyTarget.getCurrentTarget().equals("forge")) {
                CONFIG.modpackBranding.updateProvidedByModpack();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //WorldListWidget
    }

    public static Identifier id(String path) {
        return new Identifier("bolt", path);
    }
}