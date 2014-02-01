package com.caved_in.commons.warps;

import com.caved_in.commons.world.WorldHandler;
import org.bukkit.Location;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 9:39 PM
 */
public class Warp {
	@Attribute(name = "name")
	private String name;

	@Element(name = "world_name")
	private String worldName;

	@Element(name = "x")
	private double x;

	@Element(name = "y")
	private double y;

	@Element(name = "z")
	private double z;

	@Element(name = "pitch")
	private float pitch;

	@Element(name = "yaw")
	private float yaw;

	private Location location;

	public Warp(@Attribute(name = "name") String name,
				@Element(name = "world_name") String worldName,
				@Element(name = "x") double x,
				@Element(name = "y") double y,
				@Element(name = "z") double z,
				@Element(name = "pitch") float pitch,
				@Element(name = "yaw") float yaw) {
		this.name = name;
		this.worldName = worldName;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.location = new Location(WorldHandler.getWorld(worldName), x, y, z, yaw, pitch);
	}

	public Warp(String name, Location location) {
		this.name = name;
		this.location = location;
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.pitch = location.getPitch();
		this.yaw = location.getYaw();
		this.worldName = location.getWorld().getName();
	}


	public Location getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}
}
