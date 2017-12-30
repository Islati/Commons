package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
import com.caved_in.commons.debug.Debugger;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class DebugModeCommand {

    private Players players;

    public DebugModeCommand() {
        players = Commons.getInstance().getPlayerHandler();
    }

    @Command(identifier = "debug on")
    public void debugOnCommand(Player player, @Arg(name = "player", def = "?sender") MinecraftPlayer mcPlayer) {
        mcPlayer.setInDebugMode(true);
        Chat.message(player, Messages.playerDebugModeChange(mcPlayer));
    }

    @Command(identifier = "debug off")
    public void debugOffCommand(Player player, @Arg(name = "player", def = "?sender") MinecraftPlayer mcPlayer) {
        mcPlayer.setInDebugMode(false);
        Chat.message(player, Messages.playerDebugModeChange(mcPlayer));
    }

    @Command(identifier = "debug ?")
    public void debugListCommand(Player player, @Arg(name = "page", def = "1") int page) {
        Debugger.getDebugMenu().sendTo(player, page, 6);
    }

    @Command(identifier = "debug", permissions = Perms.DEBUG_MODE)
    public void onDebugModeCommand(Player player, @Arg(name = "action", def = "") String action, @Wildcard @Arg(name = "arguments") String args) {
        MinecraftPlayer minecraftPlayer = players.getData(player);
        if (action == null || action.isEmpty()) {
            minecraftPlayer.setInDebugMode(!minecraftPlayer.isInDebugMode());
            Chat.message(player, Messages.playerDebugModeChange(minecraftPlayer));
            return;
        }

        if (Debugger.isDebugAction(action)) {
            String[] debugArgs = args.split(" ");
            Chat.debug("Arguments for Debug Action " + action + " are: " + args);
            Debugger.getDebugAction(action).doAction(player, debugArgs);
            return;
        }
    }
}
