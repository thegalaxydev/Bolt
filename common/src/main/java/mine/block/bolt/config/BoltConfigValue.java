package mine.block.bolt.config;

import com.google.gson.*;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BoltConfigValue<T> {
    private final String name;
    private final Optional<T> defaultValue;
    private final Class<T> classOfT;
    private @Nullable T currentValue;

    public BoltConfigValue(String name, Class<T> classOfT, @Nullable T defaultValue) {
        this.name = name;
        this.classOfT = classOfT;
        this.defaultValue = Optional.ofNullable(defaultValue);

        this.defaultValue.ifPresent(t -> currentValue = t);
    }

    public void loadValues(JsonObject wholeConfigFile) throws Exception {
        boolean required = defaultValue.isEmpty();
        if(required && !wholeConfigFile.has(name)) {
            throw new Exception("The following value is required in Bolt's configuration: " + name);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement element = wholeConfigFile.get(name);

        if(element == null) {
            throw new Exception("Unable to find the following value in Bolt's configuration or the config was incorrect! " + name);
        }

        @Nullable T value = gson.fromJson(element, classOfT);
        currentValue = value;
    }

    public JsonElement serialize() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(this.currentValue == null) return JsonNull.INSTANCE;
        return gson.toJsonTree(this.currentValue);
    }

    public T get() {
        return currentValue;
    }

    public String getName() {
        return name;
    }

    public Optional<T> getDefaultValue() {
        return defaultValue;
    }
}
