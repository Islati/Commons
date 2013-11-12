package com.caved_in.commons.handlers.Chat;

public class ChatMessage
{
	private String playerReceivingMessage = "";
	private String playerSendingMessage = "";

	public ChatMessage(String playerSendingMessage, String playerReceivingMessage)
	{
		this.playerReceivingMessage = playerReceivingMessage;
		this.playerSendingMessage = playerSendingMessage;
	}

	public String getPlayerSendingMessage()
	{
		return playerSendingMessage;
	}

	public void setPlayerSendingMessage(String playerSendingMessage)
	{
		this.playerSendingMessage = playerSendingMessage;
	}

	public String getPlayerReceivingMessage()
	{
		return playerReceivingMessage;
	}

	public void setPlayerReceivingMessage(String playerReceivingMessage)
	{
		this.playerReceivingMessage = playerReceivingMessage;
	}
}
