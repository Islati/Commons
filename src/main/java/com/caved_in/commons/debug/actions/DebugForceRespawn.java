package com.caved_in.commons.debug.actions;


import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.player.MinecraftPlayer;
import org.bukkit.entity.Player;

public class DebugForceRespawn implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        MinecraftPlayer mcPlayer = Commons.getInstance().getPlayerHandler().getData(player);

        if (mcPlayer.hasForceRespawn()) {
            mcPlayer.setForceRespawn(false);
            Chat.message(player, "&eForce Respawn has been &cdisabled");
        } else {
            mcPlayer.setForceRespawn(true);
            Chat.message(player,"&eForce Respawn has been &aenabled");
        }
    }

    @Override
    public String getActionName() {
        return "force_respawn";
    }
}
