package com.caved_in.commons.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropListener implements Listener
{
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event)
	{
		event.setCancelled(true);
	}
}
