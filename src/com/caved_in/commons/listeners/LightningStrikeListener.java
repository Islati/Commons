package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.LightningStrikeEvent;

public class LightningStrikeListener implements Listener
{

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onLightningStrike(LightningStrikeEvent Event)
	{
		if (Commons.getConfiguration().getWorldConfig().isThunderDisabled())
		{
			Event.setCancelled(true);
		}
	}
}

