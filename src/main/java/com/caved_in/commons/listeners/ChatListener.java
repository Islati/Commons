package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		//Check if the chat is silenced
		if (Commons.getConfiguration().getWorldConfig().isChatSilenced()) {
			if (!PlayerHandler.canChatWhileSilenced(player)) {
				//Send the player a message saying the chat's silenced
				PlayerHandler.sendMessage(player, Messages.CHAT_SILENCED);
				event.setCancelled(true);
				return;
			}
		}
		event.setFormat(StringUtil.formatColorCodes(String.format("&r%s - %s",player.getDisplayName(),event.getMessage())));
		/*
		TODO Optimize this
		ChatColor playerChatColor = PlayerHandler.getData(playerName).getTagColor();
		if (event.getPlayer().isOp()) {
			event.setFormat(playerChatColor + "[Owner] " + playerDisplayName + ChatColor.RESET + " - " + event.getMessage());
		} else {
			event.setFormat(ChatColor.RESET + playerDisplayName + ChatColor.RESET + " - " + event.getMessage());
		}
		*/
	}
}
