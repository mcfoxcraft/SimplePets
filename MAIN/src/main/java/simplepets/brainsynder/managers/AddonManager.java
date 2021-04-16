package simplepets.brainsynder.managers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lib.brainsynder.json.Json;
import lib.brainsynder.json.JsonArray;
import lib.brainsynder.json.JsonObject;
import lib.brainsynder.reflection.FieldAccessor;
import lib.brainsynder.utils.AdvString;
import lib.brainsynder.web.WebConnector;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.addon.AddonConfig;
import simplepets.brainsynder.addon.AddonData;
import simplepets.brainsynder.addon.PetAddon;
import simplepets.brainsynder.utils.debug.Debug;
import simplepets.brainsynder.utils.debug.DebugBuilder;
import simplepets.brainsynder.utils.debug.DebugLevel;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AddonManager {
    private final AddonConfig addonFile;
    private final PetCore plugin;
    private final File folder;
    private final List<PetAddon> rawAddons, loadedAddons;

    public AddonManager(PetCore plugin) {
        this.plugin = plugin;
        rawAddons = Lists.newArrayList();
        loadedAddons = Lists.newArrayList();
        folder = new File(plugin.getDataFolder().toString() + File.separator + "Addons");

        addonFile = new AddonConfig(plugin.getDataFolder(), "AddonConfig.yml");

        if (!folder.exists()) return;
        for (File file : folder.listFiles()) {
            loadAddon(file);
        }
    }

    private void loadAddon(File file) {
        if (file.isDirectory()) return;
        if (!file.getName().endsWith(".jar")) return;
        try {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, this.getClass().getClassLoader());

            try (JarFile jarFile = new JarFile(file)) {
                Enumeration<JarEntry> e = jarFile.entries();
                while (e.hasMoreElements()) {
                    JarEntry je = e.nextElement();
                    if (je.isDirectory() || !je.getName().endsWith(".class")) continue;
                    String className = je.getName().substring(0, je.getName().length() - 6);
                    className = className.replace('/', '.');
                    Class<?> loadClass = urlClassLoader.loadClass(className);
                    if (!PetAddon.class.isAssignableFrom(loadClass)) continue;
                    rawAddons.add((PetAddon) loadClass.getDeclaredConstructor().newInstance());
                }
            } catch (NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }

            if (rawAddons.isEmpty())
                Debug.debug(DebugBuilder.build(getClass()).setLevel(DebugLevel.MODERATE).setMessages(
                        "Could not find a class that extends PetAddon",
                        "Are you sure '" + file.getName() + "' is an addon?"
                ));
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            Debug.debug(DebugBuilder.build(getClass()).setLevel(DebugLevel.ERROR).setMessages(
                    "Failed to load addon: " + file.getName(),
                    "Error Message: " + e.getMessage()
            ));
            e.printStackTrace();
        }
    }

    public void cleanup() {
        for (PetAddon addon : loadedAddons) {
            addon.cleanup();
            HandlerList.unregisterAll(addon);
        }

        loadedAddons.clear();
        rawAddons.clear();
    }

    public void initialize() {
        for (PetAddon addon : rawAddons) {
            try {
                String name = addon.getNamespace().namespace();
                // Set the "loaded" field to true
                FieldAccessor.getField(PetAddon.class, "loaded", Boolean.TYPE).set(addon, true);
                if (!addonFile.contains(name + ".Enabled")) {
                    addonFile.addComment(name + ".Enabled", "Enable/Disable the " + name + " addon");
                    addonFile.set(name + ".Enabled", true);
                }
                loadedAddons.add(addon);
                boolean enabled = addonFile.getBoolean(name + ".Enabled", true);
                FieldAccessor.getField(PetAddon.class, "config", AddonConfig.class).set(addon, addonFile);
                if (!isSupported(addon.getSupportedVersion())) {
                    Debug.debug(DebugBuilder.build(getClass()).setLevel(DebugLevel.MODERATE).setMessages(
                            name + " (by " + addon.getAuthor() + ") is not supported for version " + PetCore.getInstance().getDescription().getVersion()
                    ));
                    return;
                }
                FieldAccessor.getField(PetAddon.class, "enabled", Boolean.TYPE).set(addon, enabled);

                if (addon.isEnabled()) {
                    Bukkit.getPluginManager().registerEvents(addon, plugin);
                    addon.init();
                }

                Debug.debug(DebugBuilder.build(getClass()).setLevel(DebugLevel.NORMAL).setMessages(
                        "Successfully initialized the '" + name + "' Addon by " + addon.getAuthor()
                ));
            } catch (Exception e) {
                Debug.debug(DebugBuilder.build(getClass()).setLevel(DebugLevel.ERROR).setMessages(
                        "Failed to initialize addon: " + addon.getClass().getSimpleName()
                ));
                e.printStackTrace();
            }
        }
        rawAddons.clear();
    }

    public void downloadAddon(String name, String url, Runnable runnable) {
        CompletableFuture.runAsync(() -> {
            try {
                final File file = new File(folder.getAbsolutePath() + "/" + name + ".jar");
                file.delete();
                FileUtils.copyURLToFile(new URL(url), file);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        loadAddon(file);
                        initialize();
                        runnable.run();
                    }
                }.runTask(plugin);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void toggleAddon(PetAddon addon, boolean enabled) {
        if (enabled && isSupported(addon.getSupportedVersion())) {
            Bukkit.getPluginManager().registerEvents(addon, plugin);
            addon.init();
        } else {
            addon.cleanup();
            HandlerList.unregisterAll(addon);
        }
        String name = addon.getNamespace().namespace();

        addonFile.set(name + ".Enabled", enabled);
        FieldAccessor.getField(PetAddon.class, "enabled", Boolean.TYPE).set(addon, enabled);
    }

    public Optional<PetAddon> fetchAddon(String name) {
        for (PetAddon addon : loadedAddons) {
            if (addon.getNamespace().namespace().equalsIgnoreCase(name)) return Optional.of(addon);
        }
        return Optional.empty();
    }

    public void update(PetAddon addon, String url, Runnable runnable) {
        addon.cleanup();
        HandlerList.unregisterAll(addon);
        loadedAddons.remove(addon);

        downloadAddon(addon.getNamespace().namespace(), url, runnable);
    }

    public boolean isSupported(String version) {
        if ((version == null) || (version.isEmpty())) return true;
        String plugin = PetCore.getInstance().getDescription().getVersion();
        if (!plugin.contains(" (build ")) return false; // Seems to be a different format (Custom?)
        if (!version.contains(" (build ")) return false; // Seems to be a different format (Custom?)
        double main = Double.parseDouble(AdvString.before(" (build", plugin));
        double checkMain = Double.parseDouble(AdvString.before(" (build", version));

        int build = Integer.parseInt(AdvString.between("build ", ")", plugin));
        int checkBuild = Integer.parseInt(AdvString.between("build ", ")", version));

        if (main >= checkMain) {
            return build >= checkBuild;
        }
        return false;
    }

    public void checkAddons() {
        Map<String, PetAddon> addonMap = Maps.newHashMap();
        loadedAddons.forEach(addon -> addonMap.put(addon.getNamespace().namespace(), addon));

        fetchAddons(addons -> {
            List<AddonData> updateNeeded = Lists.newArrayList();

            addons.forEach(addon -> {
                if (addonMap.containsKey(addon.getName())) {
                    PetAddon petAddon = addonMap.get(addon.getName());
                    if (addon.getVersion() > petAddon.getVersion()) {
                        FieldAccessor.getField(PetAddon.class, "update", Boolean.TYPE).set(petAddon, true);
                        updateNeeded.add(addon);
                    }
                }
            });

            if (updateNeeded.isEmpty()) return;
            updateNeeded.forEach(addonData -> {

                Debug.debug(DebugBuilder.build(getClass()).setLevel(DebugLevel.UPDATE).setMessages(
                        "There is an update for the addon '" + addonData.getName() + "' version " + addonData.getVersion()
                ));
            });
        });
    }

    public List<PetAddon> getLoadedAddons() {
        return loadedAddons;
    }

    public void fetchAddons(Consumer<List<AddonData>> consumer) {
        WebConnector.getInputStreamString("https://pluginwiki.us/addons/addons.json", plugin, result -> {
            JsonArray array = (JsonArray) Json.parse(result);

            List<AddonData> addons = Lists.newArrayList();

            array.forEach(jsonValue -> {
                JsonObject json = (JsonObject) jsonValue;
                AddonData data = new AddonData();

                FieldAccessor.getField(AddonData.class, "url", String.class).set(data, json.getString("url", "UNKNOWN"));
                FieldAccessor.getField(AddonData.class, "name", String.class).set(data, json.getString("name", "UNKNOWN"));
                FieldAccessor.getField(AddonData.class, "author", String.class).set(data, json.getString("author", "UNKNOWN"));
                if (json.names().contains("supportedVersion"))
                    FieldAccessor.getField(AddonData.class, "supportedVersion", String.class).set(data, json.getString("supportedVersion", "UNKNOWN"));
                FieldAccessor.getField(AddonData.class, "version", Double.TYPE).set(data, json.getDouble("version", 0.0));

                if (json.names().contains("description")) {
                    List<String> description = Lists.newArrayList();
                    ((JsonArray) json.get("description")).forEach(value -> description.add(value.asString()));
                    FieldAccessor.getField(AddonData.class, "description", List.class).set(data, description);
                }

                addons.add(data);
            });
            consumer.accept(addons);
        });
    }

}