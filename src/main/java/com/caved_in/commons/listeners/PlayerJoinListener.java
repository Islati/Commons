package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.WorldConfiguration;
import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.world.WorldHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
		player.setFlySpeed((float) PlayerWrapper.defaultFlySpeed);
		player.setWalkSpeed((float) PlayerWrapper.defaultWalkSpeed);
		WorldConfiguration worldConfig = Commons.getConfiguration().getWorldConfig();

		if (!worldConfig.isJoinLeaveMessagesEnabled()) {
			event.setJoinMessage(null);
		}

		PlayerHandler.addData(player);
		if (worldConfig.isCompassMenuEnabled()) {
			if (!player.getInventory().contains(Material.COMPASS)) {
				player.getInventory().addItem(ItemHandler.makeItemStack(Material.COMPASS, ChatColor.GREEN + "Server Selector"));
			}
		}

		//If the players in the lobby, teleport them to the spawn when they join
		if (Commons.getConfiguration().getServerName().equalsIgnoreCase("lobby")) {
			player.teleport(WorldHandler.getSpawn(player), PlayerTeleportEvent.TeleportCause.PLUGIN);
		}
	}
}
