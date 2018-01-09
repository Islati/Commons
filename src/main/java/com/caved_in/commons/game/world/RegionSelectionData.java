package com.caved_in.commons.game.world;

import org.bukkit.Location;

public class RegionSelectionData {

    private Location upperBound = null;

    private Location lowerBound = null;

    private String name = null;

    public RegionSelectionData() {

    }

    public RegionSelectionData upper(Location loc) {
        this.upperBound = loc;
        return this;
    }

    public RegionSelectionData lower(Location loc) {
        this.lowerBound = loc;
        return this;
    }

    public RegionSelectionData name(String name) {
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

    public boolean hasLowerBound() {
        return getLowerBound() != null;
    }

    public boolean hasUpperBound() {
        return getUpperBound() != null;
    }

    public boolean hasName() {
        return getName() != null;
    }
}
