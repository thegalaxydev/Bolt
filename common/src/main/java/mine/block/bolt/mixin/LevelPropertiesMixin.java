package mine.block.bolt.mixin;

import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import mine.block.bolt.brand.BrandingConfig;
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

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin implements SimpleBrandingVersionExtension {
    @Shadow private LevelInfo levelInfo;
    private @Final @Mutable BrandingConfig.VersionInformation versionInformation;

    @Inject(method = "updateProperties", at = @At("RETURN"))
    private void bolt$updateProperties(DynamicRegistryManager registryManager, NbtCompound levelNbt, NbtCompound playerNbt, CallbackInfo ci) {
        BrandingConfig.VersionInformation versionInformation = BoltConfig.modpackBranding.get().modpackVersion;
        NbtCompound modpackVersion = new NbtCompound();
        modpackVersion.putString("ID", versionInformation.ID);
        modpackVersion.putString("releaseType", versionInformation.releaseType);
        modpackVersion.putString("semName", versionInformation.semName);
        levelNbt.put("modpackVersion", modpackVersion);
    }

    @Inject(method = "readProperties", at = @At("RETURN"))
    private static void bolt$readProperties(Dynamic<NbtElement> dynamic, DataFixer dataFixer, int dataVersion, @Nullable NbtCompound playerData, LevelInfo levelInfo, SaveVersionInfo saveVersionInfo, GeneratorOptions generatorOptions, Lifecycle lifecycle, CallbackInfoReturnable<LevelProperties> cir) {
        LevelProperties properties = cir.getReturnValue();
        BrandingConfig.VersionInformation information = new BrandingConfig.VersionInformation();
        information.ID = dynamic.get("modpackVersion").get("ID").asString("69420");
        information.semName = dynamic.get("modpackVersion").get("semName").asString("1.0.0");
        information.releaseType = dynamic.get("modpackVersion").get("releaseType").asString("Beta");
        ((SimpleBrandingVersionExtension)properties).setVersion(information);
    }


    @Override
    public BrandingConfig.VersionInformation getVersion() {
        return versionInformation;
    }

    @Override
    public void setVersion(BrandingConfig.VersionInformation version) {
        this.versionInformation = version;
    }
}
