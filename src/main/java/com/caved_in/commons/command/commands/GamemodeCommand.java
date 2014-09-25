package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GamemodeCommand {
	private static Map<String, GameMode> modeAliases = new HashMap<String, GameMode>() {{
		put("0", GameMode.SURVIVAL);
		put("s", GameMode.SURVIVAL);
		put("survival", GameMode.SURVIVAL);
		put("1", GameMode.CREATIVE);
		put("c", GameMode.CREATIVE);
		put("creative", GameMode.CREATIVE);
		put("build", GameMode.CREATIVE);
		put("2", GameMode.ADVENTURE);
		put("a", GameMode.ADVENTURE);
		put("adventure", GameMode.ADVENTURE);
	}};

	@Command(name = "gm", usage = "/gm <0/1/survival/creative> to switch gamemodes", permission = "commons.command.gamemode")
	public void onGamemodeCommand(Player player, String[] args) {
		//If the player didn't include a target, or mode argument then cycle their game-mode.
		if (args.length == 0) {
			Players.cycleGameMode(player);
			return;
		}

		String mode = args[0];
		Player target = player;

		if (args.length >= 2) {
			String targetPlayerName = args[1];
			//If the target player's not online, then quit while we're ahead!
			if (!Players.isOnline(targetPlayerName)) {
				Players.sendMessage(player, Messages.playerOffline(targetPlayerName));
				return;
			}

			target = Players.getPlayer(targetPlayerName);
		}

		mode = mode.toLowerCase();
		if (!modeAliases.containsKey(mode)) {
			Players.sendMessage(player, Messages.invalidCommandUsage("gamemode"));
			return;
		}

		target.setGameMode(modeAliases.get(mode));
	}
}
