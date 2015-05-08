package com.caved_in.commons.chat;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * The private message manager provides functionality utilized internally by Commons for private communication between players.
 */
public class PrivateMessageManager {
    //todo implement block / blacklist for messages.
    private Map<String, PrivateMessage> recentChatters = new HashMap<>();

    public PrivateMessageManager() {

    }

    /**
     * Checks whether a player has a recent chat message
     *
     * @param playerName player to check recent messages for
     * @return true if there's a recent chat message for this player, false otherwise
     */
    public boolean hasRecentPrivateMessageFrom(String playerName) {
        return recentChatters.containsKey(playerName);
    }


    /**
     * Sets the chatmessage for a player
     *
     * @param playerFor      player to set the recent chat-message for
     * @param privateMessage chatmessage to set for the player
     */
    public void setRecentPrivateMessageFrom(String playerFor, PrivateMessage privateMessage) {
        recentChatters.put(playerFor, privateMessage);
    }

    /**
     * Gets the person who sent the most recent message to the requested player
     *
     * @param playerFor player to get the recent chatter for
     * @return name of the recent chatter; null if none exists
     */
    public String getMostRecentPrivateMessager(String playerFor) {
        return recentChatters.get(playerFor).getPlayerSendingMessage();
    }


    public void messagePlayer(Player playerSendingTo, Player playerSendingFrom, String message) {
        //todo implement option to customize the format of chat messages.
        Chat.sendMessage(playerSendingTo, "&f[&e" + playerSendingFrom.getDisplayName() + "&b -> &aYou&f] " + message);
        Chat.sendMessage(playerSendingFrom, "&f[&eYou &b-> &a" + playerSendingTo.getDisplayName() + "&f] " + message);
        setRecentPrivateMessageFrom(playerSendingTo.getName(), new PrivateMessage(playerSendingFrom.getName(), playerSendingTo.getName()));
    }
}
