package com.devsteady.onyx.player;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface PlayerWrapper {
    /**
     * @return name of the player
     */
    String getName();

    /**
     * @return the players uuid
     */
    UUID getId();

    /**
     * @return player whose data is being wrapped.
     */
    Player getPlayer();

}
