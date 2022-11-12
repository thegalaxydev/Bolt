package mine.block.bolt.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

    public void loadValues(JsonObject wholeConfigFile) {
        boolean required = defaultValue.isEmpty();
        if(required && !wholeConfigFile.has(name)) {
            throw new RuntimeException("The following value is required in Bolt's configuration: " + name);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement element = wholeConfigFile.get(name);
        @Nullable T value = gson.fromJson(element, classOfT);
        currentValue = value;
    }

    public JsonElement serialize() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
