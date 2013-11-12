package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.handlers.Items.ItemHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CompassListener implements Listener
{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerInteractedItem(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		ItemStack itemInHand = player.getItemInHand();
		if (itemInHand != null && itemInHand.getType() != Material.AIR)
		{
			if (itemInHand.getType() == Material.COMPASS)
			{
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					if (ItemHandler.getItemName(itemInHand).toLowerCase().contains("server selector"))
					{
						Commons.serverMenu.getMenu().openMenu(player);
					}
				}
			}
		}
	}
}
