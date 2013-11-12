package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.TunnelsPermissions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener
{
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent Event)
	{
		if (Commons.getConfiguration().getMaintenanceConfig().isMaintenanceMode())
		{
			if (!Event.getPlayer().hasPermission(TunnelsPermissions.MAINTAINANCE_WHITELIST))
			{
				Event.setKickMessage(Commons.getConfiguration().getMaintenanceConfig().getKickMessage());
				Event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			}
		}
	}
}
