package com.caved_in.commons.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        //Cancel the event if it's changing weather
        if (event.toWeatherState()) {
            event.setCancelled(true);
        }
    }
}
