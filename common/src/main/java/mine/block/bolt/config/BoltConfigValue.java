package mine.block.bolt.config;

import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.MarshallerImpl;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BoltConfigValue<T> {
    private final String name;
    private final Optional<T> defaultValue;
    private final Class<T> classOfT;
    private final String description;
    private @Nullable T currentValue;

    public BoltConfigValue(String name, String description, Class<T> classOfT, @Nullable T defaultValue) {
        this.name = name;
        this.description = description;
        this.classOfT = classOfT;
        this.defaultValue = Optional.ofNullable(defaultValue);

        this.defaultValue.ifPresent(t -> currentValue = t);
    }

    public void loadValues(JsonObject root) throws Exception {
        if(!root.containsKey(name)) {
            throw new Exception("The following value was not found in Bolt's configuration: " + name);
        }

        T value = root.get(classOfT, getName());

        if(value == null) {
            throw new Exception("Unable to find the following value in Bolt's configuration or the config was incorrect! " + name);
        }

        currentValue = (T) value;
    }

    public void serialize(JsonObject root) {
        if(this.currentValue == null) return;
        root.putDefault(getName(), get(), getDescription());
    }

    public String getDescription() {
        return description;
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
