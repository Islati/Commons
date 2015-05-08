package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.MaintenanceConfiguration;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPingListener implements Listener {

    private MaintenanceConfiguration config;

    public ServerPingListener() {
        config = Commons.getInstance().getConfiguration().getMaintenanceConfig();
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        if (config.isMaintenanceMode()) {
            event.setMotd(StringUtil.formatColorCodes(config.getMotd()));
        }
    }
}
