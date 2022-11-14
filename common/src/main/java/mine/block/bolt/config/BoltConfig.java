package mine.block.bolt.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import mine.block.bolt.brand.BrandingConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class BoltConfig {
    private static final ArrayList<BoltConfigValue<?>> registeredConfigValues = new ArrayList<>();
    
    public static final BoltConfigValue<Boolean> clearChatHistory = createValue("clearChatHistory", Boolean.class, false);
    public static final BoltConfigValue<Boolean> disableChatClearing = createValue("disableChatClearing", Boolean.class, true);
    public static final BoltConfigValue<Boolean> disableResourcePackWarning = createValue("disableResourcePackWarning", Boolean.class, true);
    public static final BoltConfigValue<Boolean> skipLoadingTransition = createValue("skipLoadingTransition", Boolean.class, true);
    public static final BoltConfigValue<Boolean> skipToastFadeOut = createValue("skipToastFadeOut", Boolean.class, false);
    public static final BoltConfigValue<Boolean> skipTitleFadeIn = createValue("skipTitleFadeIn", Boolean.class, true);
    public static final BoltConfigValue<Boolean> disableExperimentalWarning = createValue("disableExperimentalWarning", Boolean.class, true);
    public static final BoltConfigValue<Boolean> enableItemDespawnBlink = createValue("enableItemDespawnBlink", Boolean.class, true);
    public static final BoltConfigValue<Integer> despawnBlinkSpeed = createValue("despawnBlinkSpeed", Integer.class, 6000);
    public static final BoltConfigValue<Boolean> enableQuickCrouch = createValue("enableQuickCrouch", Boolean.class, false);
    public static final BoltConfigValue<Float> quickCrouchSpeed = createValue("quickCrouchSpeed", Float.class, 10.0F);
    public static final BoltConfigValue<Integer> despawnBlinkStartTime = createValue("despawnBlinkStartTime", Integer.class, 20);
    public static final BoltConfigValue<BrandingConfig> modpackBranding = createValue("modpackBranding", BrandingConfig.class, new BrandingConfig());
    public static final BoltConfigValue<Boolean> enableCutThroughGrass = createValue("enableCutThroughGrass", Boolean.class, false);
    public static final BoltConfigValue<Boolean> enableHideHandKeybind = createValue("enableHideHandKeybind", Boolean.class, false);
    private static <T> BoltConfigValue<T> createValue(String name, Class<T> classOfT, @Nullable T defaultValue) {
        BoltConfigValue<T> value = new BoltConfigValue<T>(name, classOfT, defaultValue);
        registeredConfigValues.add(value);
        return value;
    }

    public static Path CONFIG_PATH;

    public static void initialize() throws IOException {
        System.out.println("Bolt is loading config from: " + CONFIG_PATH);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(Files.notExists(CONFIG_PATH)) {
            // No config yet. Create default.
            JsonObject obj = new JsonObject();
            for (BoltConfigValue<?> registeredConfigValue : registeredConfigValues) {
                obj.add(registeredConfigValue.getName(), registeredConfigValue.serialize());
            }

            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, gson.toJson(obj), Charset.defaultCharset());
            return;
        } else {
            String jsonRaw = Files.readString(CONFIG_PATH, Charset.defaultCharset());
            JsonObject obj = gson.fromJson(jsonRaw, JsonObject.class);
            for (BoltConfigValue<?> registeredConfigValue : registeredConfigValues) {
                registeredConfigValue.loadValues(obj);
            }
        }
    }
}
