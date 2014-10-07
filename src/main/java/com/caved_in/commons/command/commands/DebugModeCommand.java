package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
import com.caved_in.commons.debug.Debugger;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class DebugModeCommand {

	@Command(identifier = "debug on")
	public void debugOnCommand(Player player, @Arg(name = "player", def = "?sender") MinecraftPlayer mcPlayer) {
		mcPlayer.setInDebugMode(true);
		Players.sendMessage(player, Messages.playerDebugModeChange(mcPlayer));
	}

	@Command(identifier = "debug off")
	public void debugOffCommand(Player player, @Arg(name = "player", def = "?sender") MinecraftPlayer mcPlayer) {
		mcPlayer.setInDebugMode(false);
		Players.sendMessage(player, Messages.playerDebugModeChange(mcPlayer));
	}

	@Command(identifier = "debug ?")
	public void debugListCommand(Player player, @Arg(name = "page", def = "1") int page) {
		Debugger.getDebugMenu().sendTo(player, page, 6);
	}

	@Command(identifier = "debug", permissions = "commons.debugmode")
	public void onDebugModeCommand(Player player, @Arg(name = "action", def = "") String action, @Wildcard @Arg(name = "arguments") String args) {
		MinecraftPlayer minecraftPlayer = Players.getData(player);
		if (action == null || action.isEmpty()) {
			minecraftPlayer.setInDebugMode(!minecraftPlayer.isInDebugMode());
			Players.sendMessage(player, Messages.playerDebugModeChange(minecraftPlayer));
			Players.updateData(minecraftPlayer);
			return;
		}

		if (Debugger.isDebugAction(action)) {
			String[] debugArgs = args.split(" ");
			Debugger.getDebugAction(action).doAction(player, debugArgs);
			return;
		}
	}
}
