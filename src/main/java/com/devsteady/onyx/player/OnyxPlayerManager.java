package com.devsteady.onyx.player;

import com.devsteady.onyx.game.listener.IUserManagerHandler;
import com.devsteady.onyx.game.players.UserManager;
import org.bukkit.entity.Player;

public class OnyxPlayerManager extends UserManager<OnyxPlayer> implements IUserManagerHandler {

    public OnyxPlayerManager() {

    }

    @Override
    public void handleJoin(Player player) {
        addUser(player);
    }

    @Override
    public void handleLeave(Player player) {
        removeUser(player);
    }
}
