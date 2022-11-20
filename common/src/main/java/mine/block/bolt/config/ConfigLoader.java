package mine.block.bolt.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonGrammar;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static mine.block.bolt.config.BoltConfig.registeredConfigValues;

public class ConfigLoader {

    public static void updateMissingFields(JsonObject object) {
        for (BoltConfigValue<?> registeredConfigValue : registeredConfigValues) {
            if (!object.containsKey(registeredConfigValue.getName())) {
                // Missing value. Add it.
                registeredConfigValue.serialize(object);
            }
        }
    }

    public static void convertToJson5() {
        try (InputStream stream = Files.newInputStream(BoltConfig.OLD_CONFIG_PATH)) {
            JsonObject oldJson = Jankson.builder().build().load(stream);
            JsonObject newJson = new JsonObject();

            for (BoltConfigValue<?> registeredConfigValue : registeredConfigValues) {
                try {
                    registeredConfigValue.loadValues(oldJson);
                } catch (Exception e) {
                    throw new Exception("Unable to load config value! " + e.getMessage());
                }
            }

            // Rewrite
            updateMissingFields(newJson);

            String json5String = newJson.toJson(JsonGrammar.JSON5);
            Files.writeString(BoltConfig.CONFIG_PATH, json5String, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Unable to convert config to JSON5...");
        }
    }

    public static void createDefaultConfig() {
        try {
            JsonObject object = new JsonObject();
            updateMissingFields(object);
            String json5String = object.toJson(JsonGrammar.JSON5);
            Files.writeString(BoltConfig.CONFIG_PATH, json5String, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Unable to convert config to JSON5...");
        }
    }

    public static void loadConfig() throws Exception {
        try (InputStream stream = Files.newInputStream(BoltConfig.CONFIG_PATH)) {
            JsonObject json = Jankson.builder().build().load(stream);

            updateMissingFields(json);

            for (BoltConfigValue<?> registeredConfigValue : registeredConfigValues) {
                try {
                    registeredConfigValue.loadValues(json);
                } catch (Exception e) {
                    throw new Exception("Unable to load config value! " + e.getMessage());
                }
            }

            String json5String = json.toJson(JsonGrammar.JSON5);
            Files.writeString(BoltConfig.CONFIG_PATH, json5String, StandardCharsets.UTF_8);
        }
    }
}
