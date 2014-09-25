package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.WorldConfiguration;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.threading.tasks.UpdateOnlineStatusThread;
import com.caved_in.commons.world.Worlds;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerJoinListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		//Reset the players walk and fly speeds
		player.setFlySpeed((float) MinecraftPlayer.DEFAULT_FLY_SPEED);
		player.setWalkSpeed((float) MinecraftPlayer.DEFAULT_WALK_SPEED);
		WorldConfiguration worldConfig = Commons.getConfiguration().getWorldConfig();

		if (!worldConfig.hasJoinMessages()) {
			event.setJoinMessage(null);
		}

		//Initialize the wrapped player data
		Players.addData(player);

		//Update the player's online status in our data-base!
		Commons.getInstance().getThreadManager().runTaskAsync(new UpdateOnlineStatusThread(player.getUniqueId(), true));

		//If the players in the lobby, teleport them to the spawn when they join
		if (Commons.getConfiguration().getServerName().equalsIgnoreCase("lobby")) {
			player.teleport(Worlds.getSpawn(player), PlayerTeleportEvent.TeleportCause.PLUGIN);
		}
	}
}
