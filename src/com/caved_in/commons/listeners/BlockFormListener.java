package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class BlockFormListener implements Listener
{

	@EventHandler
	public void onBlockForm(BlockFormEvent Event)
	{
		Material blockType = Event.getNewState().getType();
		switch (blockType)
		{
			case SNOW:
				if (Commons.getConfiguration().getWorldConfig().isSnowSpreadDisabled())
				{
					Event.setCancelled(true);
				}
				break;
			case ICE:
				if (Commons.getConfiguration().getWorldConfig().isIceSpreadDisabled())
				{
					Event.setCancelled(true);
				}
				break;
			default:
				break;
		}
	}
}
