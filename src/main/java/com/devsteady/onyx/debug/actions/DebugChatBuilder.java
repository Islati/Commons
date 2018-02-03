package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.debug.DebugAction;
import org.bukkit.entity.Player;

public class DebugChatBuilder implements DebugAction {
	@Override
	public void doAction(Player player, String... args) {

	}

	@Override
	public String getActionName() {
		return "chat_builder";
	}
}
