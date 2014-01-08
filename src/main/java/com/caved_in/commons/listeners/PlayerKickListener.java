package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.player.PlayerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class PlayerKickListener implements Listener {
	@EventHandler
	public void onPlayerKicked(PlayerKickEvent event) {
		if (!Commons.getConfiguration().getWorldConfig().isJoinLeaveMessagesEnabled()) {
			event.setLeaveMessage(null);
		}

		String playerName = event.getPlayer().getName();
		PlayerHandler.removeData(playerName);
		FriendHandler.removeFriendList(playerName);
		Commons.disguiseDatabase.deletePlayerDisguiseData(playerName);
	}
}
