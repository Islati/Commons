package com.caved_in.commons.effect;

import com.caved_in.commons.location.Locations;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Effects {
	public static final int BLOCK_EFFECT_RADIUS = 6;

	public static final int BLEED_EFFECT_RADIUS = 10;

	/**
	 * Plays the given effect  at a specific location to all the players within the
	 * given radius of said location
	 *
	 * @param location  location to play the effect
	 * @param radius    radius to search for players
	 * @param blockType the material of the block
	 */
	public static void playBlockEffectAt(Location location, int radius, Effect effect, Material blockType) {
		for (Player player : Locations.getPlayersInRadius(location, radius)) {
			player.playEffect(location, effect, blockType.getId());
		}
	}

	/**
	 * Plays the effect of a block breaking at a specific location to all the players within the
	 * given radius of said location
	 *
	 * @param location  location to play the block break effect
	 * @param radius    radius to search for players
	 * @param blockType the material of the block
	 */
	public static void playBlockBreakEffect(Location location, int radius, Material blockType) {
		for (Player player : Locations.getPlayersInRadius(location, radius)) {
			player.playEffect(location, Effect.STEP_SOUND, blockType.getId());
		}
	}

	public static void playBlockEffect(Player player, Location location, Effect effect, Material blockType) {
		player.playEffect(location, effect, blockType.getId());
	}

	public static void strikeLightning(Location loc, boolean damage) {
		World world = loc.getWorld();
		if (damage) {
			world.strikeLightning(loc);
		} else {
			world.strikeLightningEffect(loc);
		}
	}

	public static void playBleedEffect(Player player) {
		playBlockBreakEffect(player.getLocation(), BLEED_EFFECT_RADIUS, Material.REDSTONE);
	}

	public static void playEffect(Location location, Effect effect, int data, int radius) {
		location.getWorld().playEffect(location, effect, data, radius);
	}

	public static void playEffect(Location location, Effect effect) {
		playEffect(location, effect, 1, BLEED_EFFECT_RADIUS);
	}

	public static void explode(Location location, float power, boolean setFire, boolean breakBlocks) {
		World world = location.getWorld();
		int[] xyz = Locations.getXYZ(location);
		world.createExplosion(xyz[0], xyz[1], xyz[2], power, setFire, breakBlocks);
	}

}
