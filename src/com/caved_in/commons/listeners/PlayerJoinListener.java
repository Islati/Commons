package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.handlers.Friends.FriendHandler;
import com.caved_in.commons.handlers.Items.ItemHandler;
import com.caved_in.commons.handlers.Player.PlayerHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerJoinListener implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void PlayerJoin(PlayerJoinEvent Event)
	{
		Player player = Event.getPlayer();
		if (!Commons.getConfiguration().getWorldConfig().isJoinLeaveMessagesEnabled())
		{
			Event.setJoinMessage(null);
		}
		PlayerHandler.addData(player);
		FriendHandler.addFriendList(player.getName());
		if (Commons.getConfiguration().getWorldConfig().isCompassMenuEnabled())
		{
			if (!player.getInventory().contains(Material.COMPASS))
			{
				player.getInventory().addItem(ItemHandler.makeItemStack(Material.COMPASS, ChatColor.GREEN + "Server Selector"));
			}
		}

		//If the players in the lobby, teleport them to the spawn when they join
		if (Commons.getConfiguration().getServerName().equalsIgnoreCase("lobby"))
		{
			player.teleport(player.getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
		}
	}
}
