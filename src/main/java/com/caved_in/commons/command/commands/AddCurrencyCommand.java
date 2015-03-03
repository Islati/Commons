package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.threading.executors.BukkitScheduledExecutorService;
import com.caved_in.commons.threading.tasks.GivePlayerTunnelsExpCallable;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddCurrencyCommand {


    @Command(identifier = "addcurrency", description = "Give the player some currency!", onlyPlayers = false, permissions = {Perms.COMMAND_ADD_CURRENCY})
    public void addCurrency(CommandSender sender, @Arg(name = "player", description = "player to give the money to") String playerName, @Arg(name = "amount", description = "amount of currency to give to the player!") int amt) {
        if (Players.isOnline(playerName)) {
            Player player = Players.getPlayer(playerName);
            Players.giveMoney(player, amt, true);
            Players.updateData(player);
            Chat.message(sender, Messages.playerAddedXp(player.getName(), amt));
        } else {

            BukkitScheduledExecutorService async = Commons.getInstance().getAsyncExecuter();

            ListenableFuture<Boolean> playerMoneyFutureBoolean = async.submit(new GivePlayerTunnelsExpCallable(playerName, amt));
            Futures.addCallback(playerMoneyFutureBoolean, new FutureCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    if (aBoolean) {
                        Chat.message(sender, Messages.playerAddedXp(playerName, amt));
                    } else {
                        Chat.message(sender, "Unable to locate data for " + playerName);
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Chat.message(sender, "ERROR & Unable to locate data for " + playerName);
                }
            });

        }
    }
}
