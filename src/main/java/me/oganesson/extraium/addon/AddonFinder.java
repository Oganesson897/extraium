package me.oganesson.extraium.addon;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class AddonFinder {

    private final File root;

    public AddonFinder(String path) {
        this.root = new File(path).exists() ? new File(path) : null;
    }

    public String[] readName() {
        return root.list();
    }

    public File[] readFile() {
        return root.listFiles();
    }

    @Nullable
    public File findAddon(String addonName) {
        File addon = new File(root, addonName);
        if (addon.exists() && addon.isDirectory()) {
            return addon;
        }
        return null;
    }

    @Nullable
    public AddonDesc findAddonDesc(String addonName) {
        File addon = findAddon(addonName);
        if (addon != null) {
            File descFile = new File(addon, "addon.json");
            if (descFile.exists()) {
                return AddonDesc.fromFile(descFile);
            }
        }
        return null;
    }

    public Map<String, Map.Entry<File, AddonDesc>> processAddons() {
        Map<String, Map.Entry<File, AddonDesc>> addons = new HashMap<>();
        for (File file : readFile()) {
            if (file.isDirectory()) {
                String name = file.getName();
                AddonDesc desc = findAddonDesc(name);
                if (desc != null) {
                    addons.put(name, new AbstractMap.SimpleEntry<>(file, desc));
                }
            }
        }
        return addons;
    }

}
