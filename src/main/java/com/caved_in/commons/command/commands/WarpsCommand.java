package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.menu.help.HelpScreen;
import com.caved_in.commons.menu.help.ItemFormat;
import com.caved_in.commons.menu.Menus;
import com.caved_in.commons.menu.help.PageDisplay;
import com.caved_in.commons.menu.menus.warpselection.WarpSelectionMenu;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.warp.Warps;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WarpsCommand {
	private Configuration config;

	public WarpsCommand() {
		config = Commons.getInstance().getConfiguration();
	}

	@Command(identifier = "warps", permissions = Perms.COMMAND_WARPS)
	public void onWarpsCommand(Player player, @Arg(name = "page", def = "1") int page) {
		if (config.enableWarpsMenu()) {
			WarpSelectionMenu.getMenu(page).openMenu(player);
		} else {
			HelpScreen warpsMenu = Menus.generateHelpScreen("Warps / Waypoints", PageDisplay.DEFAULT, ItemFormat.NO_DESCRIPTION, ChatColor.GREEN, ChatColor.DARK_GREEN);
			for (String warp : Warps.getWarpNames()) {
				warpsMenu.setEntry(warp, "");
			}
			warpsMenu.sendTo(player, page, 7);
		}
	}
}
