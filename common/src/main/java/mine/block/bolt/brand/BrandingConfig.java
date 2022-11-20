package mine.block.bolt.brand;

public class BrandingConfig {

    public boolean enabled = false;
    public boolean enableTitlescreenBranding = true;
    public String modpackName = "Modpack Name";
    public VersionInformation modpackVersion = new VersionInformation();
    public String modpackID = "";
    public String[] modpackAuthors = new String[]{"Author 1", "Author 2"};
    public String[] providedByModpack = new String[0];
    public ContactInformation URLS = new ContactInformation();

    public void updateProvidedByModpack() {
        ModPackProvider modPackProvider = null;
        if (!(providedByModpack.length == 1 && (modPackProvider = ModPackProvider.getFromString(providedByModpack[0])) != null)) {
            return;
        }
        modPackProvider.run(() -> providedByModpack, (s) -> providedByModpack = s);
    }

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
