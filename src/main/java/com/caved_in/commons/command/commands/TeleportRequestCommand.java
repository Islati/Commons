package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.MinecraftPlayer;
import org.bukkit.entity.Player;

public class TeleportRequestCommand {
	private static Commons commons = Commons.getInstance();

	@Command(identifier = "tpa", permissions = "commons.tpa")
	public void onTpaCommand(Player player, @Arg(name = "target") Player target) {
		commons.getPlayerHandler().getData(player).requestTeleportTo(target);
	}

	@Command(identifier = "tpahere", permissions = "commons.tpa")
	public void onTpaHereCommand(Player player, @Arg(name = "target") Player target) {
		commons.getPlayerHandler().getData(player).requestTeleportHere(target);
	}

	@Command(identifier = "tpaccept", permissions = "commons.tpa")
	public void onTpAcceptCommand(Player player) {
		MinecraftPlayer acceptor = commons.getPlayerHandler().getData(player);
		if (!acceptor.hasTeleportRequest()) {
			Chat.message(player, "&eYou do &cnot&e have any teleport requests");
			return;
		}

		acceptor.acceptTeleport();
	}

	@Command(identifier = "tpdeny", permissions = "commons.tpa")
	public void onTpDenyCommand(Player player) {
		MinecraftPlayer denier = commons.getPlayerHandler().getData(player);

		if (!denier.hasTeleportRequest()) {
			Chat.message(player, "&eYou do &cnot&e have any teleport requests");
			return;
		}

		denier.denyTeleport();
	}
}
