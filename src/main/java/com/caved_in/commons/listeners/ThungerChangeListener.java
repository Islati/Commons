package com.caved_in.commons.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;

public class ThungerChangeListener implements Listener {

    @EventHandler
    public void onThunderChange(ThunderChangeEvent event) {
        //Cancel the even if it's being changed to thunder
        if (event.toThunderState()) {
            event.setCancelled(true);
        }
    }
}
