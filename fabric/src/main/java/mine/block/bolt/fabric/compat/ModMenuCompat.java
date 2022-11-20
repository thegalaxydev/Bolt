package mine.block.bolt.fabric.compat;

import com.terraformersmc.modmenu.api.ModMenuApi;
import mine.block.bolt.config.BoltConfig;

import java.util.Arrays;
import java.util.function.Consumer;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public void attachModpackBadges(Consumer<String> consumer) {
        System.out.println("attaching badges");
        System.out.println(Arrays.toString(BoltConfig.modpackBranding.get().providedByModpack));
        for (String s : BoltConfig.modpackBranding.get().providedByModpack) {
            consumer.accept(s);
        }
        consumer.accept("minecraft");
    }
}
