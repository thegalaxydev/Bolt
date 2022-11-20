package mine.block.bolt.fabric.compat;

import com.terraformersmc.modmenu.api.ModMenuApi;
import mine.block.bolt.config.BoltConfig;

import java.util.function.Consumer;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public void attachModpackBadges(Consumer<String> consumer) {
        if (!BoltConfig.modpackBranding.get().enabled) return;
        for (String s : BoltConfig.modpackBranding.get().providedByModpack) {
            consumer.accept(s);
        }
    }
}
