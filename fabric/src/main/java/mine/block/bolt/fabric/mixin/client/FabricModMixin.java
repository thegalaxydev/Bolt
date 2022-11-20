package mine.block.bolt.fabric.mixin.client;

import com.terraformersmc.modmenu.util.mod.fabric.FabricMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = FabricMod.class, remap = false)
public class FabricModMixin {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z", ordinal = 6))
    private boolean bolt$ddd(String d, Object j) {
        if (d.equals("fabric")) return d.equals(j);
        return !d.equals(j);
    }
}
