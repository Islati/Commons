package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.threading.tasks.UpdatePlayerPremiumCallable;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.command.CommandSender;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:34 PM
 */
public class BuyPremiumCommand {
	@Command(name = "buypremium", description = "Used by the console to give users premium for a set amount of time", permission = "tunnels.common.buypremium")
	public void buyPlayerPremium(final CommandSender sender, String[] args) {
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

		ListenableFuture<Boolean> updatePremiumStatusFuture = Commons.asyncExecutor.submit(new UpdatePlayerPremiumCallable(playerName, true));
		Futures.addCallback(updatePremiumStatusFuture, new FutureCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean aBoolean) {
				if (aBoolean) {
					Players.sendMessage(sender, Messages.premiumPlayerPromoted(playerName));
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
