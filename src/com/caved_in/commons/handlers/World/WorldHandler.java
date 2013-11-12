package com.caved_in.commons.handlers.World;

import com.caved_in.commons.Commons;

import org.bukkit.World;

public class WorldHandler
{
	public static void handleWorldWeather(World World)
	{
		if (World.hasStorm() && Commons.getConfiguration().getWorldConfig().isWeatherDisabled())
		{
			World.setStorm(false);
			World.setThundering(false);
		}
	}
}
