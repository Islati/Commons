package com.devsteady.onyx.game.gadget;

import org.bukkit.entity.Player;

/**
 * Used to attach data to gadgets whenever a player performs on them.
 */
public interface GadgetAction {

    /**
     * @param player player performing the action.
     */
    void perform(Player player);
}
