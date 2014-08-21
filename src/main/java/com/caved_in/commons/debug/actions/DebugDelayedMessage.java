package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Commons;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.entity.Player;

public class DebugDelayedMessage implements DebugAction {
	@Override
	public void doAction(Player player, String... args) {
		Commons.debug("Delayed Message : " + player.getName());
		Commons.debug(args);

		int delay = StringUtil.getNumberAt(args, 0, 3);
		Commons.debug("Delay: " + delay);
		String[] messages = new String[0];
		if (args.length > 1) {
			messages = new String[args.length - 1];
			System.arraycopy(args, 1, messages, 0, args.length - 1);
		} else {
			messages = new String[]{
					"See, &eTHIS &cShit", "&b Just works d00d!", "&aSrsly ;)"
			};
		}

		Commons.debug("Messages for Delayed Message: ");
		Commons.debug(messages);

		Players.sendDelayedMessage(player, delay, messages);
	}

	@Override
	public String getActionName() {
		return "delayed_message";
	}
}
