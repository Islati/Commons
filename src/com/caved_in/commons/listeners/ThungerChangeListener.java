package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;

public class ThungerChangeListener implements Listener
{

	@EventHandler
	public void onThunderChange(ThunderChangeEvent Event)
	{
		if (Event.toThunderState() && Commons.getConfiguration().getWorldConfig().isThunderDisabled())
		{
			Event.setCancelled(true);
		}
	}
}
