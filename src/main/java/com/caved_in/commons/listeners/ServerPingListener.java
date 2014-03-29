package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.MaintenanceConfiguration;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPingListener implements Listener {
	@EventHandler
	public void onServerPing(ServerListPingEvent event) {
		MaintenanceConfiguration maintenanceConfiguration = Commons.getConfiguration().getMaintenanceConfig();
		if (maintenanceConfiguration.isMaintenanceMode()) {
			event.setMotd(StringUtil.formatColorCodes(maintenanceConfiguration.getMotd()));
		}
	}
}
