package com.caved_in.commons.debug.actions;

import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class DebugPlayerSyncData implements DebugAction {

	@Override
	public void doAction(Player player) {
		Players.updateData(Players.getData(player));
		Players.sendMessage(player, "Your data has been synchronized - Check console for errors (if any)");
	}

	@Override
	public String getActionName() {
		return "player_sync_data";
	}
}
