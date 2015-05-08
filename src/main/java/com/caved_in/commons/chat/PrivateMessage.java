package com.caved_in.commons.chat;

/**
 * Used internally to handle the private messaging between players.
 */
public class PrivateMessage {
    /* Player the message is being sent to. */
    private String playerReceivingMessage;
    /* Player sending the message */
    private String playerSendingMessage;

    public PrivateMessage(String playerSendingMessage, String playerReceivingMessage) {
        this.playerReceivingMessage = playerReceivingMessage;
        this.playerSendingMessage = playerSendingMessage;
    }

    public String getPlayerSendingMessage() {
        return playerSendingMessage;
    }

    public void setPlayerSendingMessage(String playerSendingMessage) {
        this.playerSendingMessage = playerSendingMessage;
    }

    public String getPlayerReceivingMessage() {
        return playerReceivingMessage;
    }

    public void setPlayerReceivingMessage(String playerReceivingMessage) {
        this.playerReceivingMessage = playerReceivingMessage;
    }
}
