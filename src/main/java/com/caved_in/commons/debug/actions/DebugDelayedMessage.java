package com.caved_in.commons.debug.actions;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.utilities.StringUtil;
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

        Chat.sendDelayedMessage(player, delay, messages);
    }

    @Override
    public String getActionName() {
        return "delayed_message";
    }
}
