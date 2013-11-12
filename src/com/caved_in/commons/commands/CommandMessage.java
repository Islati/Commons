package com.caved_in.commons.commands;

import org.bukkit.command.CommandSender;

public enum CommandMessage
{
	Deny("[Tunnels Network] You do not have permission for this command, if you believe this is an error please fill out a bug report on our forums."),
	Error("[Tunnels Network] There was an error while processing this command; Please check the syntax used, and if it persists fill out a bug report on our forums."),
	NonExist("[Tunnels Network] That command does not exist.");

	private final String Message;

	CommandMessage(String Message)
	{
		this.Message = Message;
	}

	public String getMessage()
	{
		return Message;
	}

	public static void sendPlayerMessage(CommandSender sender, CommandMessage message)
	{
		sender.sendMessage(message.getMessage());
	}
}
