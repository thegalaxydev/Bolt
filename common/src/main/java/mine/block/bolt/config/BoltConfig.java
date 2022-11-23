package mine.block.bolt.config;

import blue.endless.jankson.Comment;
import mine.block.bolt.brand.BrandingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;

public class BoltConfig {
    public static Path OLD_CONFIG_PATH;
    public static Path CONFIG_PATH;

    @Comment("Allows you to control whether chat history is cleared.")
    public boolean clearChatHistory = false;
    @Comment("Whether or not chat messages fade out, letting you scroll up infinitely since you joined the world/server.")
    public boolean disableChatClearing = true;
    @Comment("Disable the annoying resource pack warning.")
    public boolean disableResourcePackWarning = true;
    @Comment("Skips the loading screen fade.")
    public boolean skipLoadingTransition = true;
    @Comment("Skips toast's fading out effect and disables the fade out sound effect.")
    public boolean skipToastFadeOut = false;
    @Comment("Skips the title screen's fade in effect.")
    public boolean skipTitleFadeIn = true;
    @Comment("Disables the experimental warning when joining modded worlds or worlds with datapacks enabled.")
    public boolean disableExperimentalWarning = true;
    @Comment("Makes it so items blink when they're about to despawn.")
    public boolean enableItemDespawnBlink = true;
    @Comment("The amount of time in milliseconds before the item should start blinking.")
    public int despawnBlinkSpeed = 6000;
    @Comment("The amount of time in milliseconds between a single blink.")
    public int despawnBlinkStartTime = 20;
    @Comment("If quick crouching should be enabled - should crouching be sped up?")
    public boolean enableQuickCrouch = false;
    @Comment("The time that it takes to crouch in milliseconds.")
    public float quickCrouchSpeed = 10.0F;
    @Comment("Allows the player to attack entities through grass.")
    public boolean enableCutThroughGrass = false;
    @Comment("Allows the hands of the player to be hidden when pressing a keybind (F10 by default)")
    public boolean enableHideHandKeybind = false;
    public BrandingConfig modpackBranding = new BrandingConfig();

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


    // README.md tooling
    public static void main(String[] args) throws Exception {
        CONFIG_PATH = Path.of("./bolt.config.json5");
        OLD_CONFIG_PATH = Path.of("./bolt.config.json");
        initialize();
        System.out.println("| __**Config Key**__| __**Default Value**__ | __**Description**__|\n" +
                "|-------------|----------------|----------------|");
        BoltConfig config = new BoltConfig();
        for (Field field : BoltConfig.class.getFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                Comment comment = getAnnotationOfField(field);
                System.out.println("|%s|%s|%s|".formatted(field.getName(), field.get(config), comment.value()));
            }
        }
    }

    private static Comment getAnnotationOfField(Field field) {
        try {
            var d = field.getAnnotation(Comment.class);
            return d.value() == null ? getAnnotationOfField(null) : d;
        } catch (NullPointerException nullPointerException) {
            return new Comment() {

                @Override
                public Class<? extends Annotation> annotationType() {
                    return Comment.class;
                }

                @Override
                public String value() {
                    return "";
                }
            };
        }
    }
}
