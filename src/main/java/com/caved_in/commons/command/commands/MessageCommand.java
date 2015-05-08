package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.PrivateMessage;
import com.caved_in.commons.chat.PrivateMessageManager;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
import com.caved_in.commons.permission.Perms;
import org.bukkit.entity.Player;

public class MessageCommand {
    private PrivateMessageManager pmManager;

    public MessageCommand() {
        pmManager = Commons.getInstance().getPrivateMessageManager();
    }

    @Command(identifier = "msg", permissions = {Perms.COMMAND_MESSAGE})
    public void onMessageCommand(Player player, @Arg(name = "receiver") Player target, @Wildcard @Arg(name = "message") String message) {
        messagePlayer(target, player, message);
    }

    private void messagePlayer(Player playerSendingTo, Player playerSendingFrom, String message) {
        Chat.sendMessage(playerSendingTo, "&f[&e" + playerSendingFrom.getDisplayName() + "&b -> &aYou&f] " + message);
        Chat.sendMessage(playerSendingFrom, "&f[&eYou &b-> &a" + playerSendingTo.getDisplayName() + "&f] " + message);
        pmManager.setRecentPrivateMessageFrom(playerSendingTo.getName(), new PrivateMessage(playerSendingFrom.getName(), playerSendingTo.getName()));
    }
}
