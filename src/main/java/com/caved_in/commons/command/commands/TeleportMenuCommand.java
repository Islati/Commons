package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import org.bukkit.entity.Player;

public class TeleportMenuCommand {

    @Command(identifier = "tpmenu")
    public void onTpMenuCommand(Player player, @Arg(name = "option") String option) {
        switch (option) {
            case "enable":
            case "on":
                Commons.TeleportMenuSettings.getInstance().enableMenu(player.getUniqueId());
                Chat.actionMessage(player, "&aYour teleport menu has been enabled!");
                break;
            case "disable":
            case "off":
                Commons.TeleportMenuSettings.getInstance().disableMenu(player.getUniqueId());
                Chat.actionMessage(player, "&cYour teleport menu has been disabled");
                break;
            default:
                Chat.message(player, Messages.invalidCommandUsage("(enable/on) / (disable/off)"));
                break;
        }
    }

}
