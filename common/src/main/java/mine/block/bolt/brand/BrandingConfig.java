package mine.block.bolt.brand;

import mine.block.bolt.util.CFLocator;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BrandingConfig {
    private static enum ModPackProvider {
        CF_SEARCH((getter, setter) -> {
            try {
                setter.accept(new CFLocator().getCFMods());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        private BiConsumer<Supplier<String[]>, Consumer<String[]>> consumer;
        ModPackProvider(BiConsumer<Supplier<String[]>, Consumer<String[]>> consumer) {
            this.consumer = consumer;
        }
        public boolean matches(String str) {
            return str.equals(String.format("%%%s%%", this));
        }

        public void run(Supplier<String[]> getter, Consumer<String[]> setter) {
            this.consumer.accept(getter, setter);
        }

        public static ModPackProvider getFromString(String str) {
            if (str.startsWith("%") && str.endsWith("%")) {
                str = str.substring(1, str.length()-1);
                return valueOf(str);
            } else {
                return null;
            }
        }
    }

    public boolean enabled = false;
    public boolean enableTitlescreenBranding = true;
    public String modpackName = "Modpack Name";
    public VersionInformation modpackVersion = new VersionInformation();
    public String modpackID = "";
    public String[] modpackAuthors = new String[] { "Author 1", "Author 2" };
    public String[] providedByModpack = new String[0];



    public void updateProvidedByModpack() {
        ModPackProvider modPackProvider = null;
        if (!(providedByModpack.length == 1 && (modPackProvider = ModPackProvider.getFromString(providedByModpack[0])) != null)) {
            return;
        }
        modPackProvider.run(() -> providedByModpack, (s) -> providedByModpack = s);
    }

    public ContactInformation URLS = new ContactInformation();
    public static class VersionInformation {
        public String ID = "69420";
        public String semName = "1.0.0";
        public String releaseType = "Beta";

        @Override
        public String toString() {
            return "VersionInformation{" +
                    "ID='" + ID + '\'' +
                    ", semName='" + semName + '\'' +
                    ", releaseType='" + releaseType + '\'' +
                    '}';
        }
    }

    public static class ContactInformation {
        public String website = "https://example.com/emergency-meeting";
        public String support = "https://discord.gg/red-is-the-imposter";
        public String repository = "https://github.com/iamdefinitely/notsus";
    }
}
