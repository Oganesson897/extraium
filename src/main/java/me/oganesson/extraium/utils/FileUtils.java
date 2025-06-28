package me.oganesson.extraium.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUtils {

    public static void copyFolder(File sourceDir, File targetDir) throws IOException {
        if (!sourceDir.isDirectory()) {
            throw new IllegalArgumentException("Source must be a directory: " + sourceDir.getAbsolutePath());
        }

        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        File[] files = sourceDir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory() && file.getName().equalsIgnoreCase("addons")) continue; // Ignore addons copy
            if (file.getName().equalsIgnoreCase("addon.json")) continue; // Ignore addon.json copy

            File targetFile = new File(targetDir, file.getName());

            if (file.isDirectory()) {
                copyFolder(file, targetFile);
            } else {
                Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

}
