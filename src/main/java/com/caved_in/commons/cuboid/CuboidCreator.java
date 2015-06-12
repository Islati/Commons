package com.caved_in.commons.cuboid;

import org.bukkit.Location;

/**
 * @Author Brandon
 * @Author Jake
 * @Project Raiders
 */
public class CuboidCreator {
    private Location first = null;

    private Location second = null;

    public CuboidCreator() {
    }

    public boolean hasPointsSelected() {
        return hasFirstPoint() && hasSecondPoint();
    }

    public boolean hasFirstPoint() {
        return first != null;
    }

    public boolean hasSecondPoint() {
        return second != null;
    }

    public CuboidCreator firstPoint(Location l) {
        this.first = l;
        return this;
    }

    public CuboidCreator secondPoint(Location l) {
        this.second = l;
        return this;
    }

    public Cuboid create() {
        if (!hasPointsSelected()) {
            throw new IllegalAccessError("Unable to create cuboid without both points selected");
        }

        return Cuboids.create(first, second);
    }

    public CuboidCreator clearPoints() {
        first = null;
        second = null;
        return this;
    }
}
