package com.devsteady.onyx.location;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Set;

public class BaseLocation extends Location {
    public BaseLocation(Location loc) {
        super(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    public BaseLocation(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public BaseLocation(World world, double x, double y, double z, float yaw, float pitch) {
        super(world, x, y, z, yaw, pitch);
    }

    public Set<Player> getPlayersInRadius(double radius) {
        return Locations.getPlayersInRadius(this, radius);
    }


}
