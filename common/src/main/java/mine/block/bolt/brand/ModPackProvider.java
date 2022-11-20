package mine.block.bolt.brand;

import mine.block.bolt.util.CFLocator;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

enum ModPackProvider {
    CF_SEARCH((getter, setter) -> {
        try {
            setter.accept(new CFLocator().getCFMods());
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
    private final BiConsumer<Supplier<String[]>, Consumer<String[]>> consumer;

    ModPackProvider(BiConsumer<Supplier<String[]>, Consumer<String[]>> consumer) {
        this.consumer = consumer;
    }

    public static ModPackProvider getFromString(String str) {
        if (str.startsWith("%") && str.endsWith("%")) {
            str = str.substring(1, str.length() - 1);
            return valueOf(str);
        } else {
            return null;
        }
    }

    public boolean matches(String str) {
        return str.equals(String.format("%%%s%%", this));
    }

    public void run(Supplier<String[]> getter, Consumer<String[]> setter) {
        this.consumer.accept(getter, setter);
    }
}
