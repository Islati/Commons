package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.utilities.StringUtil;
import org.bukkit.entity.Player;

public class DebugDelayedMessage implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        int delay = StringUtil.getNumberAt(args, 0, 3);
        String[] messages = new String[0];
        if (args.length > 1) {
            messages = new String[args.length - 1];
            System.arraycopy(args, 1, messages, 0, args.length - 1);
        } else {
            messages = new String[]{
                    "See, &eTHIS &cShit", "&b Just works d00d!", "&aSrsly ;)"
            };
        }

        Chat.messageDelayed(player, delay, messages);
    }

    @Override
    public String getActionName() {
        return "delayed_message";
    }
}
