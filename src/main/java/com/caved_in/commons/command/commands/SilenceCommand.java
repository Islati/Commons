package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import org.bukkit.command.CommandSender;

public class SilenceCommand {
    @Command(identifier = "silence", permissions = Perms.COMMAND_SILENCE, onlyPlayers = false)
    public void onSilenceCommand(CommandSender sender) {
        Commons.getInstance().getConfiguration().silenceChat(true);
        Chat.messageAll(Messages.CHAT_SILENCED);
    }
}
