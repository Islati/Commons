package com.caved_in.commons.menu.anvil;

import org.bukkit.entity.Player;

public interface AnvilInputHandler {
    /**
     * Method to process the players reply inside the Anvil GUI.
     * @param player player who's using the GUI
     * @param reply what the player wrote in the input box.
     * @return null to close the inventory window, or a string to display a message to the player in the input box.
     */
    String onClick(Player player, String reply);
}
