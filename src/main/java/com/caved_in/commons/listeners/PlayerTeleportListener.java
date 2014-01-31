package com.caved_in.commons.listeners;

import com.caved_in.commons.player.PlayerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 11:21 PM
 */
public class PlayerTeleportListener implements Listener {
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (!event.isCancelled()) {
			PlayerHandler.getData(event.getPlayer()).setPreTeleportLocation(event.getFrom());
		}
	}
}
