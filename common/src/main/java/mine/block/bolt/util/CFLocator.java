package mine.block.bolt.util;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mine.block.bolt.Bolt;
import mine.block.bolt.brand.BrandingConfig;
import mine.block.bolt.config.BoltConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class CFLocator {
    private static final String BOLT_CONFIG_CACHE_JSON = "bolt-config-cache.json";
    private static final String MINECRAFT_INSTANCE_JSON = "minecraftinstance.json";
    private static final String INSTALLED_FILE = "installedFile";
    private static final String FILE_NAME = "fileName";
    private static final String ID = "id";
    private static final String FABRIC_MOD_JSON = "fabric.mod.json";
    private static final String MODS = "mods";
    private static final String MODS_PREFIX = "mods/";
    private static final String MODPACK_VERSION = "modpackVersion";
    private static final String INSTALLED_ADDONS = "installedAddons";
    private final Gson gson = new Gson();

    public static String getHash() {
        BrandingConfig config = Bolt.CONFIG.modpackBranding;
        return Hashing.sha256().hashString(config.modpackID + config.modpackName + config.modpackVersion.semName + config.modpackVersion.releaseType + config.modpackVersion.ID, StandardCharsets.UTF_8).toString();
    }

    public String[] getCFMods() throws IOException {
        return getCFMods(true);
    }

    public String[] getCFMods(boolean d) throws IOException {
        File bolt_cache = PlatformSpecifics.getConfigDir().resolve(BOLT_CONFIG_CACHE_JSON).toFile();
        if (bolt_cache.exists() && d) {
            ArrayList<String> mods = new ArrayList<>();
            var buf = new BufferedReader(new FileReader(bolt_cache));
            StringBuilder stringBuilder = new StringBuilder();
            buf.lines().forEach((stringBuilder::append));
            String string = stringBuilder.toString();
            JsonElement obj = gson.fromJson(string, JsonElement.class);
            if (!obj.isJsonObject()) return getCFMods(false);
            JsonElement modpackVersion = obj.getAsJsonObject().get(MODPACK_VERSION);
            if (modpackVersion.isJsonPrimitive()) {
                String ver = modpackVersion.getAsString();
                if (!matchHash(ver)) {
                    return getCFMods(false);
                }
            } else {
                return getCFMods(false);
            }
            JsonElement ele = obj.getAsJsonObject().get(MODS);
            if (ele.isJsonArray()) {
                ele.getAsJsonArray().forEach(e -> {
                    if (e.isJsonPrimitive()) {
                        mods.add(e.getAsString());
                    }
                });
            } else {
                return getCFMods(false);
            }
            return mods.toArray(new String[0]);
        } else {
            String[] mods = locateCFMods();
            bolt_cache.createNewFile();
            BufferedWriter fw = new BufferedWriter(new FileWriter(bolt_cache));
            JsonObject object = new JsonObject();
            object.addProperty(MODPACK_VERSION, getHash());
            JsonArray modsList = new JsonArray();
            for (String mod : mods) {
                modsList.add(mod);
            }
            object.add(MODS, modsList);
            fw.write(gson.toJson(object));
            fw.close();
            return mods;
        }
    }

    private boolean matchHash(String ver) {
        BrandingConfig config = Bolt.CONFIG.modpackBranding;
        return Hashing.sha256().hashString(config.modpackID + config.modpackName + config.modpackVersion.semName + config.modpackVersion.releaseType + config.modpackVersion.ID, StandardCharsets.UTF_8).toString().equals(ver);
    }

    private String[] locateCFMods() throws IOException {
        ArrayList<String> mods = new ArrayList<>();
        Path cfInstance = Paths.get(MINECRAFT_INSTANCE_JSON);
        var buf = new BufferedReader(new FileReader(cfInstance.toFile()));
        StringBuilder stringBuilder = new StringBuilder();
        buf.lines().forEach((stringBuilder::append));
        String string = stringBuilder.toString();
        JsonElement ele = gson.fromJson(string, JsonElement.class);
        if (ele.isJsonObject()) {
            JsonObject obj = ele.getAsJsonObject();
            JsonElement installedAddons;
            if (obj.has(INSTALLED_ADDONS) && (installedAddons = obj.get(INSTALLED_ADDONS)).isJsonArray()) {
                JsonArray installedAddonsArray = installedAddons.getAsJsonArray();
                installedAddonsArray.forEach(jsonElement -> {
                    if (jsonElement.isJsonObject() && jsonElement.getAsJsonObject().has(INSTALLED_FILE) && jsonElement.getAsJsonObject().get(INSTALLED_FILE).isJsonObject()) {
                        JsonObject installedFile = jsonElement.getAsJsonObject().get(INSTALLED_FILE).getAsJsonObject();
                        if (installedFile.has(FILE_NAME) && installedFile.get(FILE_NAME).isJsonPrimitive()) {
                            String file = installedFile.get(FILE_NAME).getAsString();
                            if (Paths.get(MODS_PREFIX + file).toFile().exists())
                                try {
                                    ZipFile zipFile = new ZipFile(MODS_PREFIX + file);
                                    ZipEntry entry = zipFile.getEntry(FABRIC_MOD_JSON); // we don't need forge as forge doesn't have this
                                    if (entry != null) {
                                        BufferedReader r = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
                                        StringBuilder stringBuilder2 = new StringBuilder();
                                        r.lines().forEach((stringBuilder2::append));
                                        String string2 = stringBuilder2.toString();
                                        JsonElement e = gson.fromJson(string2, JsonElement.class);
                                        if (e.isJsonObject()) {
                                            if (e.getAsJsonObject().has(ID) && e.getAsJsonObject().get(ID).isJsonPrimitive()) {
                                                String id = e.getAsJsonObject().get(ID).getAsString();
                                                mods.add(id);
                                            }
                                        }
                                    }
                                } catch (IOException e) {
                                    // mod not found
                                }
                        }
                    }
                });
            }
        }
        return mods.toArray(mods.toArray(new String[0]));
    }


}
