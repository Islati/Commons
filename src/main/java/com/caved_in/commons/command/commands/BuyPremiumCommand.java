package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.threading.executors.BukkitScheduledExecutorService;
import com.caved_in.commons.threading.tasks.UpdatePlayerPremiumCallable;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.command.CommandSender;

public class BuyPremiumCommand {
	@Command(identifier = "buypremium", description = "Used to give users premium", permissions = Perms.COMMAND_BUYPREMIUM)
	public void buyPlayerPremium(CommandSender sender, @Arg(name = "player") String playerName) {
		Players.sendMessage(sender, "&ePlease wait while we search for this players info");
		//If the player's online, then get the unique ID of the player

		if (!Players.hasPlayed(playerName)) {
			Players.sendMessage(sender, Messages.invalidPlayerData(playerName));
			return;
		}

		ListenableFuture<Boolean> updatePremiumStatusFuture;
		BukkitScheduledExecutorService async = Commons.getInstance().getAsyncExecuter();

		if (Players.isOnline(playerName)) {
			updatePremiumStatusFuture = async.submit(new UpdatePlayerPremiumCallable(Players.getPlayer(playerName).getUniqueId(), true));
		} else {
			updatePremiumStatusFuture = async.submit(new UpdatePlayerPremiumCallable(playerName, true));
		}


		// = Commons.getInstance().getAsyncExecuter().submit(new UpdatePlayerPremiumCallable(playerName, true));
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
