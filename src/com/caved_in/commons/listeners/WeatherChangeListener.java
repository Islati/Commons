package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener
{

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent Event)
	{
		if (Event.toWeatherState() && Commons.getConfiguration().getWorldConfig().isWeatherDisabled())
		{
			Event.setCancelled(true);
		}
	}
}
