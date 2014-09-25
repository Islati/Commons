package com.caved_in.commons.location;

import org.bukkit.Location;

public class BaseLocation extends Location {
	public BaseLocation(Location loc) {
		super(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}
}
