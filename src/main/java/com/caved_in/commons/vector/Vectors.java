package com.caved_in.commons.vector;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Random;

public class Vectors {
	private static final Random random = new Random();

	public static Vector getSpread(Location location, double amount) {
		double yaw = Math.toRadians(-location.getYaw() - 90f);
		double pitch = Math.toRadians(-location.getPitch());
		double[] spread = {1, 1, 1};
		for (int i = 0; i < 3; i++) {
			spread[i] = ((random.nextDouble() - random.nextDouble()) * amount * 0.1);
		}

		double x = Math.cos(pitch) * Math.cos(yaw) + spread[0];
		double y = Math.sin(pitch) + spread[1];
		double z = -Math.sin(yaw) * Math.cos(pitch) + spread[2];
		return new Vector(x, y, z);
	}

	public static Vector subtract(Location from, Location to) {
		return to.toVector().subtract(from.toVector());
	}

	public static Vector direction(Location from, Location to) {
		return subtract(from, to).normalize();
	}

}
