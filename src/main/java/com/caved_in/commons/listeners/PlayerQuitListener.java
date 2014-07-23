package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.player.Players;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuitListener implements Listener {
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		UUID playerId = event.getPlayer().getUniqueId();
		if (!Commons.getConfiguration().getWorldConfig().hasJoinMessages()) {
			event.setQuitMessage(null);
		}
		Players.removeData(playerId);
//
//		if (Commons.hasSqlBackend()) {
//			Commons.disguiseDatabase.deletePlayerDisguiseData(playerId);
//		}
	}
}
