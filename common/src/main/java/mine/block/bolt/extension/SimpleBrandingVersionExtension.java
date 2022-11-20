package mine.block.bolt.extension;

import mine.block.bolt.brand.SimpleVersionInformation;

public interface SimpleBrandingVersionExtension {
    default SimpleVersionInformation getVersion() {
        throw new AssertionError();
    }

    default void setVersion(SimpleVersionInformation version) {
        throw new AssertionError();
    }
}
