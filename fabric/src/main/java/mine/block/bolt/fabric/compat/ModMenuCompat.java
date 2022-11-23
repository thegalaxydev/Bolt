package mine.block.bolt.fabric.compat;

import com.terraformersmc.modmenu.api.ModMenuApi;
import mine.block.bolt.Bolt;
import mine.block.bolt.config.BoltConfig;

import java.util.function.Consumer;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public void attachModpackBadges(Consumer<String> consumer) {
        if (!Bolt.CONFIG.modpackBranding.enabled) return;
        for (String s : Bolt.CONFIG.modpackBranding.providedByModpack) {
            consumer.accept(s);
        }
    }
}
