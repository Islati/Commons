package com.caved_in.commons.command.handlers;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.ArgumentHandler;
import com.caved_in.commons.command.CommandArgument;
import com.caved_in.commons.command.CommandError;
import com.caved_in.commons.command.TransformError;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MinecraftPlayerArgumentHandler extends ArgumentHandler<MinecraftPlayer> {

	public MinecraftPlayerArgumentHandler() {
		setMessage("player_not_online", "%1 isn't online!");

		addVariable("sender", "The command executor", (sender, argument, varName) -> {
			if (!(sender instanceof Player)) {
				throw new CommandError(Messages.CANT_AS_CONSOLE);
			}

			Player player = (Player) sender;
			MinecraftPlayer mcPlayer = Players.getData(player);
			if (mcPlayer == null) {
				throw new CommandError("No MinecraftPlayer data exists for " + player.getName());
			}
			return mcPlayer;
		});
	}

	@Override
	public MinecraftPlayer transform(CommandSender sender, CommandArgument argument, String value) throws TransformError {
		Player player = Players.getPlayer(value);

		if (player == null) {
			throw new TransformError(Messages.playerOffline(value));
		}

		return Players.getData(player);
	}
}
