package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

public class BlockSpreadListener implements Listener
{

	@EventHandler
	public void onBlockSpread(BlockSpreadEvent Event)
	{
		if (Event.getSource().getType() == Material.MYCEL && Commons.getConfiguration().getWorldConfig().isMyceliumSpreadDisabled())
		{
			Event.setCancelled(true);
		}
	}
}
