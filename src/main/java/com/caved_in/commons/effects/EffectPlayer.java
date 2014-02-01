package com.caved_in.commons.effects;

import com.caved_in.commons.location.LocationHandler;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 26/11/13
 * Time: 11:48 AM
 */
public class EffectPlayer {
	/**
	 * Plays the given effect  at a specific location to all the players within the
	 * given radius of said location
	 *
	 * @param location  location to play the effect
	 * @param radius    radius to search for players
	 * @param blockType the material of the block
	 */
	public static void playBlockEffectAt(Location location, int radius, Effect effect, Material blockType) {
		for (Player player : LocationHandler.getPlayersInRadius(location, radius)) {
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
		for (Player player : LocationHandler.getPlayersInRadius(location, radius)) {
			player.playEffect(location, Effect.STEP_SOUND, blockType.getId());
		}
	}

	public static void playBlockEffect(Player player, Location location, Effect effect, Material blockType) {
		player.playEffect(location, effect, blockType.getId());
	}
}
