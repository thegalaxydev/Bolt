package mine.block.bolt.mixin;

import com.mojang.serialization.Dynamic;
import mine.block.bolt.brand.BrandingConfig;
import mine.block.bolt.brand.SimpleVersionInformation;
import mine.block.bolt.extension.SimpleBrandingVersionExtension;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.level.LevelInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static mine.block.bolt.brand.SimpleVersionInformation.DEFAULT;

@Mixin(LevelInfo.class)
public class LevelInfoMixin implements SimpleBrandingVersionExtension {
    private @Final @Mutable SimpleVersionInformation version;
    @Override
    public SimpleVersionInformation getVersion() {
        return version;
    }

    @Override
    public void setVersion(SimpleVersionInformation version) {
        this.version = version;
    }

    @Inject(method = "withGameMode", at = @At("RETURN"))
    public void bolt$withGameMode(GameMode mode, CallbackInfoReturnable<LevelInfo> cir) {
        cir.getReturnValue().setVersion(version);
    }

    @Inject(method = "fromDynamic", at = @At("RETURN"))
    private static void bolt$fromDynamic(Dynamic<?> dynamic, DataPackSettings dataPackSettings, CallbackInfoReturnable<LevelInfo> cir) {
        SimpleVersionInformation information = new SimpleVersionInformation(
                dynamic.get("modpackVersion").get("modpackName").asString(DEFAULT.modpackName()),
                dynamic.get("modpackVersion").get("modpackID").asString(DEFAULT.modpackID()),
                dynamic.get("modpackVersion").get("ID").asString(DEFAULT.ID()),
                dynamic.get("modpackVersion").get("semName").asString(DEFAULT.semName()),
                dynamic.get("modpackVersion").get("releaseType").asString(DEFAULT.releaseType())
        );
        cir.getReturnValue().setVersion(information);
    }

    @Inject(method = "withDifficulty", at = @At("RETURN"))
    public void bolt$withDifficulty(Difficulty difficulty, CallbackInfoReturnable<LevelInfo> cir) {
        cir.getReturnValue().setVersion(version);
    }

    @Inject(method = "withDataPackSettings", at = @At("RETURN"))
    public void bolt$withDataPackSettings(DataPackSettings dataPackSettings, CallbackInfoReturnable<LevelInfo> cir) {
        cir.getReturnValue().setVersion(version);
    }

    @Inject(method = "withCopiedGameRules", at = @At("RETURN"))
    public void bolt$withCopiedGameRules(CallbackInfoReturnable<LevelInfo> cir) {
        cir.getReturnValue().setVersion(version);
    }
}
