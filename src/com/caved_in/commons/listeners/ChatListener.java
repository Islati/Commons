package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.handlers.Player.PlayerHandler;
import com.caved_in.commons.handlers.Utilities.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener
{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void PlayerChat(AsyncPlayerChatEvent Event)
	{
		String playerName = Event.getPlayer().getName();
		String playerDisplayName = Event.getPlayer().getDisplayName();
		/*
		 * if (PlayerHandler.isInStaffChat(Event.getPlayer().getName())) {
		 * PluginMessage.sendStaffChatMessage(Event.getPlayer(),
		 * Event.getMessage());
		 * PlayerHandler.sendToAllStaff(Event.getPlayer().getName
		 * (),Event.getMessage()); Event.setCancelled(true); return; }
		 */

		if (Commons.getConfiguration().getWorldConfig().isChatSilenced())
		{
			if (PlayerHandler.canChatWhileSilenced(Event.getPlayer()) == false)
			{
				Event.getPlayer().sendMessage(ChatColor.GRAY + "Chat is currently silenced, you are only able to chat if you have the required permissions");
				Event.setCancelled(true);
				return;
			}
		}
		ChatColor playerChatColor = PlayerHandler.getData(playerName).getTagColor();
		if (Event.getPlayer().isOp())
		{
			Event.setFormat(playerChatColor + "[Owner] " + playerDisplayName + ChatColor.RESET + " - " + Event.getMessage());
		}
		else
		{
			Event.setFormat(ChatColor.RESET + playerDisplayName + ChatColor.RESET + " - " + Event.getMessage());
		}
		//Event.setFormat((playerChatColor == ChatColor.AQUA ? "[Owner] " + ChatColor.RESET : playerChatColor) + playerDisplayName + " - " + ChatColor.RESET + Event.getMessage());
	}
}
