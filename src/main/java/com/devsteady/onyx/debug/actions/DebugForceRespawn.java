package com.devsteady.onyx.debug.actions;


import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.player.OnyxPlayer;
import org.bukkit.entity.Player;

public class DebugForceRespawn implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        OnyxPlayer mcPlayer = Onyx.getInstance().getPlayerHandler().getUser(player);

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
