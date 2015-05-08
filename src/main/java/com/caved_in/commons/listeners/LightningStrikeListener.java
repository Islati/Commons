package com.caved_in.commons.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.LightningStrikeEvent;

public class LightningStrikeListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLightningStrike(LightningStrikeEvent event) {
        //Cancel the event; This listener is only registered if the config has lightning disabled
        event.setCancelled(true);
    }
}

