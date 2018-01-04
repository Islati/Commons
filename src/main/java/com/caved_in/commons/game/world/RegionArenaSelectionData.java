package com.caved_in.commons.game.world;

import org.bukkit.Location;

public class RegionArenaSelectionData {

    private Location upperBound = null;

    private Location lowerBound = null;

    private String name = null;

    public RegionArenaSelectionData() {

    }

    public RegionArenaSelectionData upper(Location loc) {
        this.upperBound = loc;
        return this;
    }

    public RegionArenaSelectionData lower(Location loc) {
        this.lowerBound = loc;
        return this;
    }

    public RegionArenaSelectionData name(String name) {
        this.name = name;
        return this;
    }

    public Location getLowerBound() {
        return lowerBound;
    }

    public Location getUpperBound() {
        return upperBound;
    }

    public String getName() {
        return name;
    }
}
