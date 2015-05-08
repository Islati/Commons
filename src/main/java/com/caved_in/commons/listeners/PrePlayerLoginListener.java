package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.sql.ServerDatabaseConnector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PrePlayerLoginListener implements Listener {

    private static ServerDatabaseConnector database;

    public PrePlayerLoginListener() {
        database = Commons.getInstance().getServerDatabase();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {

    }
}
