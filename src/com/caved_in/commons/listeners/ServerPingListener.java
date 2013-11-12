package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.handlers.Utilities.StringUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPingListener implements Listener
{
	@EventHandler
	public void serverListPingEvent(ServerListPingEvent Event)
	{
		if (Commons.getConfiguration().getMaintenanceConfig().isMaintenanceMode())
		{
			Event.setMotd(StringUtil.formatColorCodes(Commons.getConfiguration().getMaintenanceConfig().getMotd()));
		}
	}
}
