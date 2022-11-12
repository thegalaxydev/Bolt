package mine.block.bolt.world;

import mine.block.bolt.fetchers.EntityDataFetcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.PortalForcer;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FixedPortalManager {

    public static BlockLocating.Rectangle findPortalAt(PlayerEntity player, RegistryKey<World> dim, BlockPos pos) {
        MinecraftServer server = player.world.getServer();
        if (server != null) {
            ServerWorld fromWorld = server.getWorld(dim);
            if (fromWorld != null) {
                PortalForcer portalForcer = fromWorld.getPortalForcer();
                return portalForcer.getPortalRect(pos, false, fromWorld.getWorldBorder()).orElse(null);
            }
        }

        return null;
    }

    public static NbtList getPlayerPortalList(PlayerEntity player) {
        NbtCompound data = ((EntityDataFetcher) (Object) player).getCustomNbtData();
        NbtList list = data.getList("FixedPortalList", NbtElement.COMPOUND_TYPE);
        data.put("FixedPortalList", list);
        return list;
    }

    @Nullable
    public static FixedPortal findFixedPortal(ServerPlayerEntity player, RegistryKey<World> fromDim, BlockPos fromPos) {
        NbtList portalList = getPlayerPortalList(player);
        for (NbtElement entry : portalList) {
            NbtCompound portal = (NbtCompound) entry;
            RegistryKey<World> entryFromDim = RegistryKey.of(Registry.WORLD_KEY, new Identifier(portal.getString("FromDim")));
            if (entryFromDim == fromDim) {
                BlockPos portalTrigger = BlockPos.fromLong(portal.getLong("FromPos"));
                if (portalTrigger.getSquaredDistance(fromPos) <= 16) {
                    UUID uid = portal.containsUuid("UID") ? portal.getUuid("UID") : UUID.randomUUID();
                    BlockPos lowerLeft = BlockPos.fromLong(portal.getLong("ToLowerLeft"));
                    int width = portal.getInt("ToWidth");
                    int height = portal.getInt("ToHeight");
                    return new FixedPortal(uid, new BlockLocating.Rectangle(lowerLeft, width, height));
                }
            }
        }

        return null;
    }

    public static void storeFixedPortal(ServerPlayerEntity player, RegistryKey<World> fromDim, BlockPos fromPos, BlockLocating.Rectangle toPortal) {
        NbtList portalList = getPlayerPortalList(player);
        FixedPortal returnPortal = findFixedPortal(player, fromDim, fromPos);
        if (returnPortal != null) {
            removeFixedPortal(player, returnPortal);
        }

        NbtCompound portalCompound = new NbtCompound();
        portalCompound.putUuid("UID", UUID.randomUUID());
        portalCompound.putString("FromDim", String.valueOf(fromDim));
        portalCompound.putLong("FromPos", fromPos.asLong());
        portalCompound.putLong("ToMinCorner", toPortal.lowerLeft.asLong());
        portalCompound.putInt("ToAxis1Size", toPortal.width);
        portalCompound.putInt("ToAxis2Size", toPortal.height);
        portalList.add(portalCompound);
    }

    public static void removeFixedPortal(ServerPlayerEntity player, FixedPortal portal) {
        // This doesn't check if it's the right toDim, but it's probably so rare for positions to actually overlap that I don't care
        NbtList portalList = getPlayerPortalList(player);
        for (int i = 0; i < portalList.size(); i++) {
            NbtCompound entry = (NbtCompound) portalList.get(i);
            if (entry.containsUuid("UID") && entry.getUuid("UID").equals(portal.uid())) {
                portalList.remove(i);
                break;
            }
        }
    }
}