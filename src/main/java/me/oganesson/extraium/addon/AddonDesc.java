package me.oganesson.extraium.addon;

import com.github.bsideup.jabel.Desugar;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;

@Desugar
public record AddonDesc(String id, String name, String version, String author, String description) {

    public AddonDesc(String id, String name, String version) {
        this(id, name, version, "Unknown", "No description provided.");
    }

    public AddonDesc(String id, String name, String version, String author) {
        this(id, name, version, author, "No description provided.");
    }

    public AddonDesc(String id, String name, String version, String author, String description) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.author = author;
        this.description = description;
    }

    public static AddonDesc fromFile(File file) {
        if (file.exists() && file.isFile()) {
            try {
                Gson gson = new Gson();
                return gson.fromJson(new FileReader(file), AddonDesc.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String author() {
        return author;
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }
}
