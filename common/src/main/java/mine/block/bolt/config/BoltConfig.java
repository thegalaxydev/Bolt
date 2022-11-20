package mine.block.bolt.config;

import mine.block.bolt.brand.BrandingConfig;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class BoltConfig {
    public static final ArrayList<BoltConfigValue<?>> registeredConfigValues = new ArrayList<>();

    public static final BoltConfigValue<Boolean> clearChatHistory = createValue("clearChatHistory", "Allows you to control whether chat history is cleared.", Boolean.class, false);
    public static final BoltConfigValue<Boolean> disableChatClearing = createValue("disableChatClearing", "Whether or not chat messages fade out, letting you scroll up infinitely since you joined the world/server.", Boolean.class, true);
    public static final BoltConfigValue<Boolean> disableResourcePackWarning = createValue("disableResourcePackWarning", "Disable the annoying resource pack warning.", Boolean.class, true);
    public static final BoltConfigValue<Boolean> skipLoadingTransition = createValue("skipLoadingTransition", "Skips the loading screen fade.", Boolean.class, true);
    public static final BoltConfigValue<Boolean> skipToastFadeOut = createValue("skipToastFadeOut", "Skips toast's fading out effect and disables the fade out sound effect.", Boolean.class, false);
    public static final BoltConfigValue<Boolean> skipTitleFadeIn = createValue("skipTitleFadeIn", "Skips the title screen's fade in effect.", Boolean.class, true);
    public static final BoltConfigValue<Boolean> disableExperimentalWarning = createValue("disableExperimentalWarning", " Disables the experimental warning when joining modded worlds or worlds with datapacks enabled.", Boolean.class, true);
    public static final BoltConfigValue<Boolean> enableItemDespawnBlink = createValue("enableItemDespawnBlink", "Makes it so items blink when they're about to despawn.", Boolean.class, true);
    public static final BoltConfigValue<Integer> despawnBlinkSpeed = createValue("despawnBlinkSpeed", "The amount of time in milliseconds before the item should start blinking.", Integer.class, 6000);
    public static final BoltConfigValue<Integer> despawnBlinkStartTime = createValue("despawnBlinkStartTime", "The amount of time in milliseconds between a single blink.", Integer.class, 20);
    public static final BoltConfigValue<Boolean> enableQuickCrouch = createValue("enableQuickCrouch", "If quick crouching should be enabled - should crouching be sped up?", Boolean.class, false);
    public static final BoltConfigValue<Float> quickCrouchSpeed = createValue("quickCrouchSpeed", "The time that it takes to crouch in milliseconds.", Float.class, 10.0F);
    public static final BoltConfigValue<Boolean> enableCutThroughGrass = createValue("enableCutThroughGrass", "Allows the player to attack entities through grass.", Boolean.class, false);
    public static final BoltConfigValue<Boolean> enableHideHandKeybind = createValue("enableHideHandKeybind", "Allows the hands of the player to be hidden when pressing a keybind (F10 by default)", Boolean.class, false);
    public static final BoltConfigValue<BrandingConfig> modpackBranding = createValue("modpackBranding", "", BrandingConfig.class, new BrandingConfig());
    private static <T> BoltConfigValue<T> createValue(String name, String description, Class<T> classOfT, @Nullable T defaultValue) {
        BoltConfigValue<T> value = new BoltConfigValue<T>(name, description, classOfT, defaultValue);
        registeredConfigValues.add(value);
        return value;
    }

    public static Path OLD_CONFIG_PATH;
    public static Path CONFIG_PATH;

    public static final Logger LOGGER = LoggerFactory.getLogger("BoltConfig");

    public static void initialize() throws Exception {

        if(Files.exists(OLD_CONFIG_PATH)) {
            LOGGER.info("Converting previous config to JSON5");
            ConfigLoader.convertToJson5();
            return;
        }

        if(!Files.exists(CONFIG_PATH)) {
            ConfigLoader.createDefaultConfig();
            return;
        }

        ConfigLoader.loadConfig();
    }
}
