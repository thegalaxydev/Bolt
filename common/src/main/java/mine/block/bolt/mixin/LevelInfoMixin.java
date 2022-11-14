package mine.block.bolt.mixin;

import com.mojang.serialization.Dynamic;
import mine.block.bolt.brand.BrandingConfig;
import mine.block.bolt.extension.SimpleBrandingVersionExtension;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelInfo.class)
public class LevelInfoMixin implements SimpleBrandingVersionExtension {
    private @Final @Mutable BrandingConfig.VersionInformation version;
    @Override
    public BrandingConfig.VersionInformation getVersion() {
        return version;
    }

    @Override
    public void setVersion(BrandingConfig.VersionInformation version) {
        this.version = version;
    }

    @Inject(method = "withGameMode", at = @At("RETURN"))
    public void bolt$withGameMode(GameMode mode, CallbackInfoReturnable<LevelInfo> cir) {
        ((LevelInfoMixin)(Object)cir.getReturnValue()).setVersion(version);
    }

    @Inject(method = "fromDynamic", at = @At("RETURN"))
    private static void bolt$fromDynamic(Dynamic<?> dynamic, DataPackSettings dataPackSettings, CallbackInfoReturnable<LevelInfo> cir) {
        BrandingConfig.VersionInformation information = new BrandingConfig.VersionInformation();
        information.ID = dynamic.get("modpackVersion").get("ID").asString("69420");
        information.semName = dynamic.get("modpackVersion").get("semName").asString("1.0.0");
        information.releaseType = dynamic.get("modpackVersion").get("releaseType").asString("Beta");
        ((SimpleBrandingVersionExtension)(Object)cir.getReturnValue()).setVersion(information);
    }

    @Inject(method = "withDifficulty", at = @At("RETURN"))
    public void bolt$withDifficulty(Difficulty difficulty, CallbackInfoReturnable<LevelInfo> cir) {
        ((LevelInfoMixin)(Object)cir.getReturnValue()).setVersion(version);
    }

    @Inject(method = "withDataPackSettings", at = @At("RETURN"))
    public void bolt$withDataPackSettings(DataPackSettings dataPackSettings, CallbackInfoReturnable<LevelInfo> cir) {
        ((LevelInfoMixin)(Object)cir.getReturnValue()).setVersion(version);
    }

    @Inject(method = "withCopiedGameRules", at = @At("RETURN"))
    public void bolt$withCopiedGameRules(CallbackInfoReturnable<LevelInfo> cir) {
        ((LevelInfoMixin)(Object)cir.getReturnValue()).setVersion(version);
    }
}
