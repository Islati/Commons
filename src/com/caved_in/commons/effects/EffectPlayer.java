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

	public static void playBlockEffectAt(Location location, int radius, Effect effect, Material blockType) {
		for (Player player : LocationHandler.getPlayersInRadius(location, radius)) {
			player.playEffect(location, effect, blockType.getId());
		}
	}

	public static void playBlockEffect(Player player, Location location, Effect effect, Material blockType) {
		player.playEffect(location, effect, blockType.getId());
	}
}
