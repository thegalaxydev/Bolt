package mine.block.bolt.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonGrammar;
import blue.endless.jankson.JsonObject;
import mine.block.bolt.Bolt;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ConfigLoader {

    public static void convertToJson5() {
        var jankson = Jankson.builder().build();
        try (InputStream stream = Files.newInputStream(BoltConfig.OLD_CONFIG_PATH)) {
            JsonObject oldJson = jankson.load(stream);
            var newJson = (JsonObject) jankson.toJson(new BoltConfig());
            oldJson.forEach((a, b) -> {
                newJson.put(a, b);
            });
            String json5String = newJson.toJson(JsonGrammar.JSON5);
            Files.writeString(BoltConfig.CONFIG_PATH, json5String, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Unable to convert config to JSON5...");
        }
    }

    public static void createDefaultConfig() {
        try {
            var d = new BoltConfig();
            var jankson = Jankson.builder().build().toJson(d).toJson(JsonGrammar.builder()
                    .withComments(true)
                    .bareSpecialNumerics(true)
                    .build());
            String json5String = jankson;
            Files.writeString(BoltConfig.CONFIG_PATH, json5String, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Unable to convert config to JSON5...");
        }
    }

    public static void loadConfig() throws Exception {
        try (InputStream stream = Files.newInputStream(BoltConfig.CONFIG_PATH)) {
            JsonObject json = Jankson.builder().build().load(stream);
            Bolt.CONFIG = Jankson.builder().build().fromJson(json, BoltConfig.class);
            String json5String = json.toJson(JsonGrammar.JSON5);
            Files.writeString(BoltConfig.CONFIG_PATH, json5String, StandardCharsets.UTF_8);
        }
    }
}
