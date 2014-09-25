package com.caved_in.commons.command.commands;

import com.caved_in.commons.command.Command;
import org.bukkit.entity.Player;


public class NicklistCommand {
	@Command(name = "nicklist", permission = "commons.command.nicklist")
	public void getNickListCommand(Player player, String[] args) {
		/*HelpScreen HelpScreen = new HelpScreen("Currently Disguised Players");
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
		*/
	}
}
