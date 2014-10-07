package com.caved_in.commons.config;

import com.caved_in.commons.location.BaseLocation;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Location;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Serializable wrapper for Locations. Extends location class so it can be passed to methods without calling getLocation()
 */
@Root(name = "location")
public class XmlLocation extends BaseLocation {
	@Element(name = "world")
	private String worldName;

	@Element(name = "x-pos")
	private double x;

	@Element(name = "y-pos")
	private double y;

	@Element(name = "z-pos")
	private double z;

	@Element(name = "pitch", required = false)
	private float pitch;

	@Element(name = "yaw", required = false)
	private float yaw;

	private Location location = null;

	public static XmlLocation fromLocation(Location loc) {
		return new XmlLocation(loc);
	}

	public XmlLocation(Location location) {
		super(location);
		worldName = Worlds.getWorldName(location);
		x = location.getX();
		y = location.getY();
		z = location.getZ();
		pitch = location.getPitch();
		yaw = location.getYaw();
	}

	public XmlLocation(@Element(name = "world") String worldName, @Element(name = "x-pos") double x, @Element(name = "y-pos") double y, @Element(name = "z-pos") double z, @Element(name = "pitch", required = false) float pitch, @Element(name = "yaw", required = false) float yaw) {
		super(Worlds.getWorld(worldName), x, y, z, pitch, yaw);
		this.worldName = worldName;
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
	}

	public String getWorldName() {
		return worldName;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public float getPitch() {
		return pitch;
	}

	@Override
	public float getYaw() {
		return yaw;
	}

	@Deprecated
	public Location getLocation() {
		if (location == null) {
			location = new Location(Worlds.getWorld(worldName), x, y, z, yaw, pitch);
		}
		return location;
	}
}
