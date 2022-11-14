package mine.block.bolt.mixin;

import mine.block.bolt.fetchers.EntityDataFetcher;
import mine.block.bolt.world.FixedPortal;
import mine.block.bolt.world.FixedPortalManager;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ServerPlayerEntity.class)
public class MixinServerEntity implements EntityDataFetcher {
    @Unique
    private NbtCompound customNbtData = new NbtCompound();
    @Override
    public NbtCompound getCustomNbtData() {
        return customNbtData;
    }

    @Override
    public void setCustomNbtData(NbtCompound tag) {
        this.customNbtData = tag;
    }

    @Inject(method = "getPortalRect", at = @At("RETURN"), cancellable = true)
    public void getExitPortal(ServerWorld destWorld, BlockPos destPos, boolean destIsNether, WorldBorder worldBorder, CallbackInfoReturnable<Optional<BlockLocating.Rectangle>> cir) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        BlockPos fromPos = player.getBlockPos();
        final RegistryKey<World> fromDim = player.world.getRegistryKey();
        final RegistryKey<World> toDim = destWorld.getRegistryKey();
        final RegistryKey<World> OVERWORLD = World.OVERWORLD;
        final RegistryKey<World> THE_NETHER = World.NETHER;
        boolean isPlayerCurrentlyInPortal = player.getInNetherPortal();
        boolean isTeleportBetweenNetherAndOverworld = (fromDim == OVERWORLD && toDim == THE_NETHER)
                || (fromDim == THE_NETHER && toDim == OVERWORLD);
        if (isPlayerCurrentlyInPortal && isTeleportBetweenNetherAndOverworld) {
            FixedPortal returnPortal = FixedPortalManager.findFixedPortal(player, fromDim, fromPos);
            MinecraftServer server = player.getServer();
            if (server == null || returnPortal == null) {
                return;
            }

            World toWorld = server.getWorld(toDim);
            if (toWorld == null) {
                return;
            }

            cir.setReturnValue(Optional.of(returnPortal.rectangle()));
        }
    }
}