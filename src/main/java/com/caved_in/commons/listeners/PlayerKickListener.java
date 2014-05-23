package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.player.Players;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class PlayerKickListener implements Listener {
	@EventHandler
	public void onPlayerKicked(PlayerKickEvent event) {
		if (!Commons.getConfiguration().getWorldConfig().hasJoinMessages()) {
			event.setLeaveMessage(null);
		}

		Players.removeData(event.getPlayer().getUniqueId());
//		if (Commons.hasSqlBackend()) {
//			Commons.disguiseDatabase.deletePlayerDisguiseData(playerName);
//		}
	}
}
