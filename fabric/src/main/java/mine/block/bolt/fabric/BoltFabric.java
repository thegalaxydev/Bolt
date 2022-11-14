package mine.block.bolt.fabric;

import mine.block.bolt.Bolt;
import mine.block.bolt.Constants;
import mine.block.bolt.config.BoltConfig;
import mine.block.bolt.util.Keybinds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;

public class BoltFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Bolt.init(FabricLoader.getInstance().getConfigDir());

        if(BoltConfig.enableHideHandKeybind.get()) {
            KeyBindingHelper.registerKeyBinding(Keybinds.hideHandKeybind);
            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                while (Keybinds.hideHandKeybind.wasPressed()) {
                    Constants.canHideHand = true;
                }
                Constants.canHideHand = false;
            });
        }


    }
}