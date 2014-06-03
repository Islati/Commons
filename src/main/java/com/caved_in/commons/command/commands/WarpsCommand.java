package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.menu.HelpScreen;
import com.caved_in.commons.menu.ItemFormat;
import com.caved_in.commons.menu.Menus;
import com.caved_in.commons.menu.PageDisplay;
import com.caved_in.commons.menu.menus.warpselection.WarpSelectionMenu;
import com.caved_in.commons.utilities.StringUtil;
import com.caved_in.commons.warp.Warps;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WarpsCommand {
	@Command(name = "warps", permission = "commons.commands.warps")
	public void onWarpsCommand(Player player, String[] args) {
//		if (Warps.getWarpCount() < 1) {
//			Players.sendMessage(player, Messages.NO_WARPS);
//			return;
//		}

		int page = 1;

		if (args.length > 0) {
			page = StringUtil.getNumberAt(args, 0, 1);
		}

		if (Commons.getWarpConfig().isWarpsMenuEnabled()) {
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
