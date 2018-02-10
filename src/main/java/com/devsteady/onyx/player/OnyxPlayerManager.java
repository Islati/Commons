package com.devsteady.onyx.player;

import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.game.listener.IUserManagerHandler;
import com.devsteady.onyx.game.players.UserManager;
import org.bukkit.entity.Player;

public class OnyxPlayerManager extends UserManager<OnyxPlayer> implements IUserManagerHandler {

    private Onyx onyx;
    public OnyxPlayerManager(Onyx onyx) {
        super(OnyxPlayer.class);
        setParent(onyx);
    }

    @Override
    public void handleJoin(Player player) {
        addUser(new OnyxPlayer(player));
    }

    @Override
    public void handleLeave(Player player) {
        removeUser(player);
    }
}
