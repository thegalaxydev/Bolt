package mine.block.bolt.fabric;

import mine.block.bolt.Bolt;
import net.fabricmc.api.ModInitializer;

public class BoltFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Bolt.init();
    }
}