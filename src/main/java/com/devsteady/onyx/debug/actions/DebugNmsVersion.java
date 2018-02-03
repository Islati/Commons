package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.plugin.Plugins;
import org.bukkit.entity.Player;

public class DebugNmsVersion implements DebugAction {
	@Override
	public void doAction(Player player, String... args) {
		Chat.message(player, String.format("&7Minecraft NMS Version is &l%s", Plugins.getNmsVersion()));
	}

	@Override
	public String getActionName() {
		return "debug_nms_version";
	}
}
