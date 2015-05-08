package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.PrivateMessage;
import com.caved_in.commons.chat.PrivateMessageManager;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.mysql.jdbc.StringUtils;
import org.bukkit.entity.Player;

public class QuickResponseCommand {

    private static PrivateMessageManager pmManager;

    public QuickResponseCommand() {
        pmManager = Commons.getInstance().getPrivateMessageManager();
    }

    @Command(identifier = "r", permissions = Perms.COMMAND_MESSAGE)
    public void quickRespondMessage(Player player, @Wildcard @Arg(name = "message") String message) {
        String playerName = player.getName();

        if (StringUtils.isNullOrEmpty(message)) {
            Chat.message(player, Messages.MESSAGE_REQUIRED);
            return;
        }

        if (!pmManager.hasRecentPrivateMessageFrom(playerName)) {
            Chat.sendMessage(player, Messages.NO_RECENT_MESSAGES);
            return;
        }

        String receiver = pmManager.getMostRecentPrivateMessager(playerName);

        if (!Players.isOnline(receiver)) {
            Chat.message(player, Messages.playerOffline(receiver));
            return;
        }

        Player playerSendingTo = Players.getPlayer(receiver);


        Chat.message(playerSendingTo, "&r[&e" + player.getName() + "&b -> &aYou&r] " + message);
        Chat.message(player, "&r[&eYou&b -> &a" + playerSendingTo.getName() + "&r] " + message);
        pmManager.setRecentPrivateMessageFrom(playerSendingTo.getName(), new PrivateMessage(player.getName(), playerSendingTo.getName()));
    }
}
