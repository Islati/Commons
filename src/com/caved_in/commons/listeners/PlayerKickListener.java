package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.handlers.Friends.FriendHandler;
import com.caved_in.commons.handlers.Player.PlayerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class PlayerKickListener implements Listener
{
	@EventHandler
	public void playerKicked(PlayerKickEvent event)
	{
		if (!Commons.getConfiguration().getWorldConfig().isJoinLeaveMessagesEnabled())
		{
			event.setLeaveMessage(null);
		}

		String playerName = event.getPlayer().getName();
		PlayerHandler.removeData(playerName);
		FriendHandler.removeFriendList(playerName);
		Commons.disguiseDatabase.deletePlayerDisguiseData(playerName);
	}
}
