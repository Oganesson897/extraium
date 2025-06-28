package me.oganesson.extraium.core;

import me.oganesson.extraium.ui.AddonSelectGUI;
import net.minecraftforge.fml.relauncher.IFMLCallHook;

import javax.swing.*;
import java.io.File;
import java.util.Map;

public class ExtraiumSetup implements IFMLCallHook {

    private File gameDir;

    @Override
    public void injectData(Map<String, Object> data) {
        this.gameDir = (File) data.get("mcLocation");
    }

    @Override
    public Void call() {
        AddonSelectGUI addonSelectGUI = new AddonSelectGUI(gameDir);
        return null;
    }
}
