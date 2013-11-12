package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.TunnelsPermissions;
import com.caved_in.commons.handlers.Friends.FriendHandler;
import com.caved_in.commons.handlers.Items.ItemHandler;
import com.caved_in.commons.handlers.Player.PlayerHandler;
import com.caved_in.commons.handlers.World.WorldHandler;
import com.caved_in.commons.commands.CommandMessage;
import com.caved_in.commons.handlers.Data.Bans.Punishment;
import com.caved_in.commons.handlers.Data.Bans.PunishmentType;
import com.caved_in.commons.handlers.Misc.TimeHandler;
import com.caved_in.commons.handlers.Utilities.StringUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class CommandPreProcessListener implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void CommandPreProcess(PlayerCommandPreprocessEvent Event)
	{
		String Command = Event.getMessage().toLowerCase();
		if (Command.startsWith("/plugins") || Command.startsWith("/pl") || Command.startsWith("/?") || Command.startsWith("/version"))
		{
			if (!Event.getPlayer().isOp())
			{
				Event.getPlayer().sendMessage(CommandMessage.Deny.getMessage());
				Event.setCancelled(true);
			}
		}
	}


}
