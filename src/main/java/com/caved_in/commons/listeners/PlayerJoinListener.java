package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.world.Worlds;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerJoinListener implements Listener {
	private Configuration config;
	boolean trackOnline = false;

	private static Commons commons = Commons.getInstance();

	public PlayerJoinListener() {
		config = Commons.getInstance().getConfiguration();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		//Reset the players walk and fly speeds
		player.setFlySpeed((float) MinecraftPlayer.DEFAULT_FLY_SPEED);
		player.setWalkSpeed((float) MinecraftPlayer.DEFAULT_WALK_SPEED);

		if (!config.enableJoinMessages()) {
			event.setJoinMessage(null);
		}

		//Initialize the wrapped player data
		commons.getPlayerHandler().addData(player);

		//If the players in the lobby, teleport them to the spawn when they join
		if (config.teleportToSpawnOnJoin()) {
			player.teleport(Worlds.getSpawn(player), PlayerTeleportEvent.TeleportCause.PLUGIN);
		}
	}
}
