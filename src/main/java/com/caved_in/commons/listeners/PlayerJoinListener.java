package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Configuration;
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
	private Configuration config;
	boolean trackOnline = false;

	private static Commons commons = Commons.getInstance();

	public PlayerJoinListener() {
		config = Commons.getInstance().getConfiguration();
		trackOnline = config.getSqlConfig().trackPlayerOnlineStatus();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		//Reset the players walk and fly speeds
		player.setFlySpeed((float) MinecraftPlayer.DEFAULT_FLY_SPEED);
		player.setWalkSpeed((float) MinecraftPlayer.DEFAULT_WALK_SPEED);
		WorldConfiguration worldConfig = config.getWorldConfig();

		if (!worldConfig.hasJoinMessages()) {
			event.setJoinMessage(null);
		}

		//Initialize the wrapped player data
		commons.getPlayerHandler().addData(player);

		//Update the player's online status in our data-base!

		if (trackOnline) {
			commons.getThreadManager().runTaskAsync(new UpdateOnlineStatusThread(player.getUniqueId(), true));
		}

		//If the players in the lobby, teleport them to the spawn when they join
		if (config.getServerName().equalsIgnoreCase("lobby")) {
			player.teleport(Worlds.getSpawn(player), PlayerTeleportEvent.TeleportCause.PLUGIN);
		}

		MinecraftPlayer mcPlayer = commons.getPlayerHandler().getData(player);

		if (commons.isServerFull() && mcPlayer.isPremium()) {
			Players.kick(Players.getRandomNonPremiumPlayer(), "&eYou were moved to hub to make room for a premium player");
		}

		if (commons.hasDatabaseBackend()) {
			commons.getThreadManager().runTaskAsync(() -> {
				commons.getServerDatabase().updatePlayerCount();
			});
		}
	}
}
