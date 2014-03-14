package com.caved_in.commons.command;

import com.caved_in.commons.Messages;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:02 PM
 */
public class ClearInventoryCommand {
	@CommandController.CommandHandler(name = "ci", usage = "/ci [player]", permission = "tunnels.common.clearinventory", aliases = {"clearinventory", "clearinv"})
	public void onClearInventoryCommand(CommandSender commandSender, String[] args) {
		//Check if we've got a player using this command
		Player player = null;
		if (args.length > 0) {
			String playerName = args[0];
			//Check if there's a player online with the name in our argument
			if (Players.isOnline(playerName)) {
				//Assign the player to clear the inventory of
				player = Players.getPlayer(playerName);
			} else {
				Players.sendMessage(commandSender, Messages.PLAYER_OFFLINE(playerName));
				return;
			}
		} else {
			//Check if the commandsender is a player
			if (commandSender instanceof Player) {
				//Assign the player variable to the player sending the command
				player = (Player) commandSender;
			} else {
				Players.sendMessage(commandSender, Messages.INVALID_COMMAND_USAGE("name"));
				return;
			}
		}
		//Clear the players inventory
		Players.clearInventory(player, true);
		//Send them a message saying their inventory was cleared
		player.sendMessage(Messages.INVENTORY_CLEARED);
	}
}
