package mine.block.bolt.util;

public class ConfigFailedToLoadException extends RuntimeException {
    public ConfigFailedToLoadException() {
        super();
    }

    public ConfigFailedToLoadException(String message) {
        super(message);
    }
}
