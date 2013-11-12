package com.caved_in.commons.listeners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockBreakListener implements Listener
{

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		if (!event.getPlayer().isOp())
		{
			if (event.getPlayer().getGameMode() != GameMode.CREATIVE)
			{
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onBlockPLace(BlockPlaceEvent event)
	{
		if (!event.getPlayer().isOp())
		{
			if (event.getPlayer().getGameMode() != GameMode.CREATIVE)
			{
				event.setCancelled(true);
			}
		}
	}
}
