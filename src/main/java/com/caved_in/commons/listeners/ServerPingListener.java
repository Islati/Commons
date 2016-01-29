package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPingListener implements Listener {

	private Configuration config;

	public ServerPingListener() {
		config = Commons.getInstance().getConfiguration();
	}

	@EventHandler
	public void onServerPing(ServerListPingEvent event) {
		if (config.isMaintenanceModeEnabled()) {
			event.setMotd(StringUtil.formatColorCodes(config.maintenanceModeMotd()));
		}
	}
}
