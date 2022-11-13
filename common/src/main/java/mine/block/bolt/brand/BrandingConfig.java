package mine.block.bolt.brand;

import com.mojang.serialization.OptionalDynamic;

public class BrandingConfig {
    public boolean enabled = false;
    public String modpackName = "Modpack Name";
    public VersionInformation modpackVersion = new VersionInformation();
    public String modpackID = "";
    public String[] modpackAuthors = new String[] { "Author 1", "Author 2" };

    public ContactInformation URLS = new ContactInformation();

    public static class VersionInformation {
        public String ID = "69420";
        public String semName = "1.0.0";
        public String releaseType = "Beta";

        public static VersionInformation deserialize(OptionalDynamic<?> modpackData) {
            VersionInformation versionInformation = new VersionInformation();
            versionInformation.ID = modpackData.get("ID").asString("69420");
            versionInformation.semName = modpackData.get("semName").asString("1.0.0");
            versionInformation.releaseType = modpackData.get("releaseType").asString("Beta");
            return versionInformation;
        }

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

        public static ContactInformation deserialize(OptionalDynamic<?> modpackData) {
            ContactInformation contactInformation = new ContactInformation();
            contactInformation.website = modpackData.get("website").asString("https://example.com/emergency-meeting");
            contactInformation.support = modpackData.get("support").asString("https://discord.gg/red-is-the-imposter");
            contactInformation.repository = modpackData.get("repository").asString("https://github.com/iamdefinitely/notsus");
            return contactInformation;
        }
    }

    public static BrandingConfig deserialize(OptionalDynamic<?> modpackData) {
        BrandingConfig brandingConfig = new BrandingConfig();
        brandingConfig.enabled = modpackData.get("enabled").asBoolean(false);
        brandingConfig.modpackName = modpackData.get("modpackName").asString("Modpack Name");
        brandingConfig.modpackVersion = VersionInformation.deserialize(modpackData.get("modpackVersion"));
        brandingConfig.modpackID = modpackData.get("modpackID").asString("");
        brandingConfig.URLS = ContactInformation.deserialize(modpackData.get("URLS"));
        return brandingConfig;
    }
}
