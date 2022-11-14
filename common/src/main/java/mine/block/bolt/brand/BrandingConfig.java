package mine.block.bolt.brand;

import com.mojang.serialization.OptionalDynamic;

public class BrandingConfig {
    public boolean enabled = false;
    public boolean enableTitlescreenBranding = true;
    public String modpackName = "Modpack Name";
    public VersionInformation modpackVersion = new VersionInformation();
    public String modpackID = "";
    public String[] modpackAuthors = new String[] { "Author 1", "Author 2" };

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
