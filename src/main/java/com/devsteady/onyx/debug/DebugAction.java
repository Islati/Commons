package com.devsteady.onyx.debug;

import com.devsteady.onyx.chat.Chat;
import org.bukkit.entity.Player;

public interface DebugAction {
    /**
     * Perform a debug action using the player as its performer
     *
     * @param player who's performing the debug action
     * @param args   extra arguments to be passed to the action.
     */
    void doAction(Player player, String... args);

    /**
     * @return the actions name.
     */
    String getActionName();

    default void debug(String... text) {
        Chat.debug(text);
    }
}
