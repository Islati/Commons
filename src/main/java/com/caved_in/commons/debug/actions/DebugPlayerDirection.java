package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import org.bukkit.entity.Player;

public class DebugPlayerDirection implements DebugAction {
	@Override
	public void doAction(Player player, String... args) {
		Chat.message(player, Messages.playerFacingDirection(player));
	}

	@Override
	public String getActionName() {
		return "player_direction";
	}
}
