package com.caved_in.commons.chat;

public class ChatMessage {
	/* Player the message is being sent to. */
	private String playerReceivingMessage;
	/* Player sending the message */
	private String playerSendingMessage;

	public ChatMessage(String playerSendingMessage, String playerReceivingMessage) {
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
