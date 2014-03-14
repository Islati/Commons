package com.caved_in.commons.command;

import com.caved_in.commons.Messages;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 5:03 PM
 */
public class TeleportAllCommand {
	@CommandController.CommandHandler(name = "tpall", usage = "/tpall", permission = "tunnels.common.tpall")
	public void onTpallCommand(Player player, String[] args) {
		String playerName = player.getName();
		for (Player onlinePlayer : Players.getOnlinePlayers()) {
			if (!onlinePlayer.getName().equals(playerName)) {
				Players.teleport(onlinePlayer, player);
				Players.sendMessage(onlinePlayer, Messages.TELEPORTED_TO_PLAYER(playerName));
			}
		}
	}
}
