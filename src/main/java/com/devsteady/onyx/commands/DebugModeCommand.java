package com.devsteady.onyx.commands;

import com.devsteady.onyx.Messages;
import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.command.Arg;
import com.devsteady.onyx.command.Command;
import com.devsteady.onyx.command.Wildcard;
import com.devsteady.onyx.debug.Debugger;
import com.devsteady.onyx.player.OnyxPlayer;
import org.bukkit.entity.Player;

public class DebugModeCommand {

    @Command(identifier = "debug ?",permissions="onyx.debug")
    public void debugListCommand(Player player, @Arg(name = "page", def = "1") int page) {
        Debugger.getDebugMenu().sendTo(player, page, 6);
    }

    @Command(identifier = "debug", permissions = "onyx.debug")
    public void onDebugModeCommand(Player player, @Arg(name = "action", def = "") String action, @Wildcard @Arg(name = "arguments") String args) {
        OnyxPlayer user = Onyx.getInstance().getPlayerHandler().getUser(player);
        if (action == null || action.isEmpty()) {
            user.setInDebugMode(!user.isInDebugMode());
            Chat.message(player, Messages.playerDebugModeChange(user));
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

