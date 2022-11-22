package mine.block.bolt.brand;

public record SimpleVersionInformation(String modpackName, String modpackID, String ID, String displayName, String semName,
                                       String releaseType) {
    public static final SimpleVersionInformation DEFAULT = new SimpleVersionInformation("Modpack Name", "", "42069", "v1", "1.0.0", "DISABLED"); // do not modify
}
