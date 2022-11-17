package mine.block.bolt.util;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.nio.file.Path;

public class PlatformSpecifics {
    @ExpectPlatform
    public static Path getConfigDir() {
        throw new AssertionError();
    }
}
