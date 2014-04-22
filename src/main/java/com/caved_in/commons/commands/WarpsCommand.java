package com.caved_in.commons.commands;

import com.caved_in.commons.menu.HelpScreen;
import com.caved_in.commons.menu.ItemFormat;
import com.caved_in.commons.menu.Menus;
import com.caved_in.commons.menu.PageDisplay;
import com.caved_in.commons.warp.Warps;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 31/01/14
 * Time: 12:00 AM
 */
public class WarpsCommand {
	@CommandController.CommandHandler(name = "warps", permission = "tunnels.common.warps")
	public void onWarpsCommand(Player player, String[] args) {
		int page = 1;
		HelpScreen warpsMenu = Menus.generateHelpScreen("Warps / Waypoints", PageDisplay.DEFAULT, ItemFormat.NO_DESCRIPTION, ChatColor.GREEN, ChatColor.DARK_GREEN);
		for (String warp : Warps.getWarps()) {
			warpsMenu.setEntry(warp, "");
		}
		if (args.length > 0) {
			String pageArg = args[0];
			if (StringUtils.isNumeric(pageArg)) {
				page = Integer.parseInt(pageArg);
			}
		}
		warpsMenu.sendTo(player, page, 7);
	}
}
