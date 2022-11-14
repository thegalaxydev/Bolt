package mine.block.bolt.fabric;

import mine.block.bolt.Constants;
import mine.block.bolt.client.ItemBlinkRenderer;
import mine.block.bolt.config.BoltConfig;
import mine.block.bolt.util.Keybinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.entity.EntityType;

public class BoltFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityType.ITEM, new ItemBlinkRenderer.Factory());

        if(BoltConfig.enableHideHandKeybind.get()) {
            KeyBindingHelper.registerKeyBinding(Keybinds.hideHandKeybind);
            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                if(!Keybinds.hideHandKeybind.isPressed()) {
                    Constants.canHideHand = false;
                }
                while (Keybinds.hideHandKeybind.wasPressed()) {
                    Constants.canHideHand = true;
                }
            });
        }
    }
}
