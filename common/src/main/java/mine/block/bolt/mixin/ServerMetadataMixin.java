package mine.block.bolt.mixin;

import com.google.gson.*;
import mine.block.bolt.brand.BrandingConfig;
import mine.block.bolt.config.BoltConfig;
import mine.block.bolt.extension.BrandingInfoExtension;
import net.minecraft.server.ServerMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;

@Mixin(ServerMetadata.class)
public class ServerMetadataMixin implements BrandingInfoExtension {

    @Unique
    public BrandingConfig data;

    @Override
    public BrandingConfig getBrandData() {
        return this.data;
    }

    @Override
    public void setBrandData(BrandingConfig serverData) {
        this.data = serverData;
    }

    @Mixin(ServerMetadata.Deserializer.class)
    public static class ServerStatusSerializerMixin {

        private static final Gson gson = new Gson();

        @Inject(method = "serialize(Lnet/minecraft/server/ServerMetadata;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", at = @At("RETURN"))
        private void serialize(ServerMetadata serverMetadata, Type type, JsonSerializationContext jsonSerializationContext, CallbackInfoReturnable<JsonElement> cir) {
            JsonObject jsonObject = cir.getReturnValue().getAsJsonObject();
            if (BoltConfig.modpackBranding.get().enabled) {
                jsonObject.add("modpackData", gson.toJsonTree(BoltConfig.modpackBranding.get()));
            } else {
                JsonObject d = new JsonObject();
                d.addProperty("notEnabled", "y");
                jsonObject.add("modpackData", d);
            }
        }

        @Inject(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/server/ServerMetadata;", at = @At("RETURN"))
        private void deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext, CallbackInfoReturnable<ServerMetadata> cir) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("modpackData")) {
                BrandingInfoExtension returnValue = cir.getReturnValue();
                if (jsonObject.get("modpackData").getAsJsonObject().has("notEnabled")) {
                    BrandingConfig d = new BrandingConfig();
                    d.enabled = false;
                    returnValue.setBrandData(d);
                    return;
                }
                BrandingConfig newValue = gson.fromJson(gson.toJson(jsonObject.get("modpackData")), BrandingConfig.class);
                returnValue.setBrandData(newValue);
            }
        }
    }
}
