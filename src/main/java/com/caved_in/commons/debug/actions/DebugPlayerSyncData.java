package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class DebugPlayerSyncData implements DebugAction {

    @Override
    public void doAction(Player player, String... args) {
        Players.updateData(Commons.getInstance().getPlayerHandler().getData(player));
        Chat.message(player, "Your data has been synchronized - Check console for errors (if any)");
    }

    @Override
    public String getActionName() {
        return "player_sync_data";
    }
}
