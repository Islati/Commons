package com.caved_in.commons.game.world;

import com.caved_in.commons.location.Locations;
import com.caved_in.commons.yml.Path;
import com.caved_in.commons.yml.YamlConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegionArena extends YamlConfig implements GameArena {
    @Path("id")
    private int id;

    @Path("name")
    @Getter @Setter
    private String name;

    @Path("enabled")
    @Getter @Setter
    private boolean enabled;

    @Path("upper-bound")
    @Getter @Setter
    private Location upperBound;

    @Path("lower-bound")
    @Getter @Setter
    private Location lowerBound;


    @Path("spawn-points")
    @Getter
    private List<Location> spawnLocations = new ArrayList<>();
    @Path("breakable-blocks")
    private List<Integer> breakableBlockIds = new ArrayList<>();

    @Path("placeable-blocks")
    private List<Integer> placeableBlockIds = new ArrayList<>();

    public RegionArena() {

    }

    public RegionArena(File file) {
        super(file);
    }

    public RegionArena(Location upperBound, Location lowerBound) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int id() {
        return id;
    }

    @Override
    public String getWorldName() {
        return upperBound.getWorld().getName();
    }

    @Override
    public World getWorld() {
        return upperBound.getWorld();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isLobby() {
        return false;
    }

    @Override
    public boolean isBreakable(Block block) {
        return breakableBlockIds.contains(block.getTypeId());
    }

    @Override
    public boolean isPlaceable(Block block) {
        return placeableBlockIds.contains(block.getTypeId());
    }

    @Override
    public boolean isRegion() {
        return true;
    }

    public Location getUpperBound() {
        return upperBound;
    }

    public Location getLowerBound() {
        return lowerBound;
    }

    public void setArenaName(String name) {
        this.name = name;
    }

    public boolean isInside(Location location) {
        return Locations.isInsideArea(location,upperBound,lowerBound,true);
    }

    public void addSpawn(Location loc) {
        spawnLocations.add(loc);
    }
}
