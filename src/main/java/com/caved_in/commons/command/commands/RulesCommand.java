package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Command;
import org.bukkit.entity.Player;

import java.util.List;

public class RulesCommand {

    @Command(identifier = "rules", description = "View the rules of the server. Obey, or bite the dust :)")
    public void onRulesCommand(Player player) {
        List<String> rules = Commons.Rules.getRules();

        for (String rule : rules) {
            Chat.message(player, String.format("%s%s", "&c&l", rule));
        }
    }

    //todo add 'rules add' command
    //todo add 'rules reload' command
}
