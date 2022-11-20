package mine.block.bolt.brand;

import blue.endless.jankson.Comment;
import com.mojang.serialization.OptionalDynamic;

public class BrandingConfig {
    @Comment("Whether to enable modpack branding.")
    public boolean enabled = false;
    @Comment("Whether to show the modpack name and version on the main menu (Fabric only.) \nPlease disable if FancyMenu is installed.")
    public boolean enableTitlescreenBranding = true;
    @Comment("The name of the modpack.")
    public String modpackName = "Modpack Name";
    @Comment("Infomation on the current version of the modpack.")
    public VersionInformation modpackVersion = new VersionInformation();
    @Comment("The ID of the modpack (curseforge/modrinth slug)")
    public String modpackID = "";
    @Comment("The authors of the modpack.")
    public String[] modpackAuthors = new String[] { "Author 1", "Author 2" };
    @Comment("Mods that are provided by the modpack - used for badges on fabric and marks any mods not in this list as non-included in crash reports.\nUse %CF_SEARCH% inside the array to automatically add values from the curseforge modpack instance json.")
    public String[] providedByModpack = new String[0];
    @Comment("URLs that will be put in the crash report to help users find support.")
    public ContactInformation URLS = new ContactInformation();

    public void updateProvidedByModpack() {
        ModPackProvider modPackProvider = null;
        if (!(providedByModpack.length == 1 && (modPackProvider = ModPackProvider.getFromString(providedByModpack[0])) != null)) {
            return;
        }
        modPackProvider.run(() -> providedByModpack, (s) -> providedByModpack = s);
    }

    public static class VersionInformation {
        @Comment("An human-readable ID of the current version.")
        public String ID = "v69-my-modpack-fabric";
        @Comment("The current semantic version of the modpack.")
        public String semName = "69.0.0";
        @Comment("The release type of the current version.\nBeta, Release or Alpha")
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
