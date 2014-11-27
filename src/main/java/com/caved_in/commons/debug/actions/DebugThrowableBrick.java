package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Messages;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.debug.gadget.ThrowableBrick;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.entity.Player;

public class DebugThrowableBrick implements DebugAction {
    private static boolean registered = false;
    private static int gadgetId;

    @Override
    public void doAction(Player player, String... args) {
        if (registered) {
            Players.giveItem(player, Gadgets.getGadget(gadgetId).getItem());
            return;
        }

        if (args.length == 0) {
            Players.sendMessage(player, Messages.invalidCommandUsage("gadget id"));
            return;
        }

        int id = StringUtil.getNumberAt(args, 0, 1995);

        Players.sendMessage(player, "&cRegistering throwable brick with id " + id);
        gadgetId = id;
        if (!Gadgets.isGadget(id)) {
            Gadgets.registerGadget(new ThrowableBrick(id));
            registered = true;
        }
        Players.giveItem(player, Gadgets.getGadget(gadgetId).getItem());
    }

    @Override
    public String getActionName() {
        return "brick_throw";
    }
}
