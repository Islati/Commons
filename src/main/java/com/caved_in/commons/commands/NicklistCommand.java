package com.caved_in.commons.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.disguises.Disguise;
import com.caved_in.commons.menu.HelpScreen;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 6:50 PM
 */
public class NicklistCommand {
	@CommandController.CommandHandler(name = "nicklist", permission = "tunnels.common.nicklist")
	public void getNickListCommand(Player player, String[] args) {
		HelpScreen HelpScreen = new HelpScreen("Currently Disguised Players");
		HelpScreen.setHeader(ChatColor.YELLOW + "<name> Page <page> of <maxpage>");
		HelpScreen.setFormat("<name> is disguised as <desc>");
		HelpScreen.setFlipColor(ChatColor.GREEN, ChatColor.DARK_GREEN);
		for (Disguise disguisedPlayer : Commons.disguiseDatabase.getDisguises()) {
			HelpScreen.setEntry(disguisedPlayer.getPlayerDisguised(), disguisedPlayer.getDisguisedAs() + " on the server '" + disguisedPlayer.getServerOn() + "'");
		}

		if (args.length > 0) {
			int Page = Integer.parseInt(args[0]);
			HelpScreen.sendTo(player, Page, 7);
		} else {
			HelpScreen.sendTo(player, 1, 7);
		}
	}
}
