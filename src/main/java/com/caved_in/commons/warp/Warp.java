package com.caved_in.commons.warp;

import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.yml.Path;
import com.caved_in.commons.yml.YamlConfig;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.io.File;

public class Warp extends YamlConfig {
    @Path("name")
    private String name;

    @Path("location")
    private Location location;

    public Warp(File file) {
        super(file);
    }
    public Warp(String name, Location location) {
        this.name = name;
        this.location = location;
    }


    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void bring(Entity entity) {
        Entities.teleport(entity, location);
    }
}
