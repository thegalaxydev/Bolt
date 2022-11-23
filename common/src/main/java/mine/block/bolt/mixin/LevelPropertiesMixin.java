package mine.block.bolt.mixin;

import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import mine.block.bolt.Bolt;
import mine.block.bolt.brand.BrandingConfig;
import mine.block.bolt.brand.SimpleVersionInformation;
import mine.block.bolt.config.BoltConfig;
import mine.block.bolt.extension.SimpleBrandingVersionExtension;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.storage.SaveVersionInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static mine.block.bolt.brand.SimpleVersionInformation.DEFAULT;

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin implements SimpleBrandingVersionExtension {
    @Shadow
    private LevelInfo levelInfo;
    private @Final
    @Mutable SimpleVersionInformation versionInformation;

    @Inject(method = "readProperties", at = @At("RETURN"))
    private static void bolt$readProperties(Dynamic<NbtElement> dynamic, DataFixer dataFixer, int dataVersion, @Nullable NbtCompound playerData, LevelInfo levelInfo, SaveVersionInfo saveVersionInfo, GeneratorOptions generatorOptions, Lifecycle lifecycle, CallbackInfoReturnable<LevelProperties> cir) {
        if (!Bolt.CONFIG.modpackBranding.enabled) return;
        LevelProperties properties = cir.getReturnValue();
        SimpleVersionInformation information = new SimpleVersionInformation(
                dynamic.get("modpackVersion").get("modpackName").asString(DEFAULT.modpackName()),
                dynamic.get("modpackVersion").get("modpackID").asString(DEFAULT.modpackID()),
                dynamic.get("modpackVersion").get("ID").asString(DEFAULT.ID()),
                dynamic.get("modpackVersion").get("displayName").asString(dynamic.get("modpackVersion").get("semName").asString(DEFAULT.semName())),
                dynamic.get("modpackVersion").get("semName").asString(DEFAULT.semName()),
                dynamic.get("modpackVersion").get("releaseType").asString(DEFAULT.releaseType())
        );
        properties.setVersion(information);
    }

    @Inject(method = "updateProperties", at = @At("RETURN"))
    private void bolt$updateProperties(DynamicRegistryManager registryManager, NbtCompound levelNbt, NbtCompound playerNbt, CallbackInfo ci) {
        if (!Bolt.CONFIG.modpackBranding.enabled) return;
        BrandingConfig brandingInfo = Bolt.CONFIG.modpackBranding;
        BrandingConfig.VersionInformation versionInformation = brandingInfo.modpackVersion;
        NbtCompound modpackVersion = new NbtCompound();
        modpackVersion.putString("ID", versionInformation.ID);
        modpackVersion.putString("releaseType", versionInformation.releaseType);
        modpackVersion.putString("displayName", versionInformation.displayName);
        modpackVersion.putString("semName", versionInformation.semName);
        modpackVersion.putString("modpackID", brandingInfo.modpackID);
        modpackVersion.putString("modpackName", brandingInfo.modpackName);
        levelNbt.put("modpackVersion", modpackVersion);
    }

    @Override
    public SimpleVersionInformation getVersion() {
        return versionInformation;
    }

    @Override
    public void setVersion(SimpleVersionInformation version) {
        this.versionInformation = version;
    }
}
