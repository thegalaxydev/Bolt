package mine.block.bolt.brand;

public class BrandingConfig {
    public boolean enabled = false;
    public String modpackName = "Modpack Name";
    public VersionInfomation modpackVersion = new VersionInfomation();
    public String modpackID = "";
    public String[] modpackAuthors = new String[] { "Author 1", "Author 2" };

    public ContactInfomation URLS = new ContactInfomation();

    public static class VersionInfomation {
        public String ID = "69420";
        public String semName = "1.0.0";
        public String releaseType = "Beta";
    }

    public static class ContactInfomation {
        public String website = "https://example.com/emergency-meeting";
        public String support = "https://discord.gg/red-is-the-imposter";
        public String repository = "https://github.com/definitely/not/sus";
    }

}
