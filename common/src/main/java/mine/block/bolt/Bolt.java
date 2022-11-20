package mine.block.bolt;

import dev.architectury.injectables.targets.ArchitecturyTarget;
import mine.block.bolt.config.BoltConfig;
import mine.block.bolt.util.PlatformSpecifics;
import net.minecraft.util.Identifier;

public class Bolt {
    public static final String MOD_ID = "bolt";

    public static void init() {
        //GameMenuScreen
        BoltConfig.CONFIG_PATH = PlatformSpecifics.getConfigDir().resolve("bolt.config.json");
        try {
            BoltConfig.initialize();
            if (BoltConfig.modpackBranding.get().enabled && !ArchitecturyTarget.getCurrentTarget().equals("forge")) {
                BoltConfig.modpackBranding.get().updateProvidedByModpack();
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