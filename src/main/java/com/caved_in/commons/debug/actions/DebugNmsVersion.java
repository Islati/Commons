package com.caved_in.commons.debug.actions;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.plugin.Plugins;
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
