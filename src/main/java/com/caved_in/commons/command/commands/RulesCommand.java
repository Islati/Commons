package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
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

    @Command(identifier = "rules add", description = "Add a new rule to the list of rules")
    public void onRulesAddCommand(Player player, @Wildcard @Arg(name = "rule") String rule) {
        Commons.Rules.add(rule);
        Chat.actionMessage(player, String.format("&eRule Added: &a%s", rule));
    }

    @Command(identifier = "rules reload", description = "Reload the rules from file")
    public void onRulesReloadCommand(Player player) {
        Commons.Rules.load();
    }
}
