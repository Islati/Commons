package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Messages;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.debug.gadget.FlamingEnderSword;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.entity.Player;

public class DebugFlamingEnderSword implements DebugAction {
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

		int id = StringUtil.getNumberAt(args, 0, 1886);

		Players.sendMessage(player, "&cRegistering flame-sword with id " + id);
		gadgetId = id;
		if (!Gadgets.isGadget(id)) {
			Gadgets.registerGadget(new FlamingEnderSword(id));
			registered = true;
		}
		Players.giveItem(player, Gadgets.getGadget(gadgetId).getItem());
	}

	@Override
	public String getActionName() {
		return "ender_sword";
	}
}
