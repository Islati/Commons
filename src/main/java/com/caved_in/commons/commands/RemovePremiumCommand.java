package com.caved_in.commons.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.threading.tasks.CallableUpdatePlayerPremium;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.command.CommandSender;

public class RemovePremiumCommand {
	@CommandController.CommandHandler(name = "removepremium", description = "Used by the console to remove premium from players", permission = "tunnels.common.removepremium")
	public void removePlayerPremium(final CommandSender sender, String[] args) {
		if (args.length == 0) {
			Players.sendMessage(sender, Messages.invalidCommandUsage("name"));
			return;
		}
		final String playerName = args[0];
		Players.sendMessage(sender, "&ePlease wait while we search for this players info");
		//If the player's online, then get the unique ID of the player

		if (!Players.hasPlayed(playerName)) {
			Players.sendMessage(sender, Messages.invalidPlayerData(playerName));
			return;
		}

		ListenableFuture<Boolean> updatePremiumStatusFuture = Commons.asyncExecutor.submit(new CallableUpdatePlayerPremium(playerName, false));
		Futures.addCallback(updatePremiumStatusFuture, new FutureCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean aBoolean) {
				if (aBoolean) {
					Players.sendMessage(sender, Messages.premiumPlayerDemoted(playerName));
				} else {
					Players.sendMessage(sender, "&cDatabase Connection Error: &eAn error occurred while connecting to the user database.", "&l&7Please report this error to a member of our staff.");
				}
			}

			@Override
			public void onFailure(Throwable throwable) {
				Players.sendMessage(sender, "&cDatabase Connection Error: &eAn error occurred while connecting to the user database.", "&l&7Please report this error to a member of our staff.");
			}
		});
	}
}
