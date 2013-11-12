package com.caved_in.commons.commands.Chat;

import com.caved_in.commons.commands.CommandController.CommandHandler;
import com.caved_in.commons.handlers.Chat.ChatHandler;
import com.caved_in.commons.handlers.Chat.ChatMessage;
import com.caved_in.commons.handlers.Player.PlayerHandler;

import com.caved_in.commons.handlers.Utilities.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatCommands
{
	@CommandHandler
	(
			name = "msg",
			usage = "/msg <player> <msg> to send a player a message"
			//permission = "tunnels.common.message"
	)
	public void Message(Player Player, String[] commandArgs)
	{
		if (commandArgs.length > 0 && !commandArgs[0].isEmpty())
		{
			String Name = commandArgs[0];
			if (!commandArgs[1].isEmpty())
			{
				String Message = "";
				for (int I = 1; I < commandArgs.length; I++)
				{
					Message += commandArgs[I] + " ";
				}

				if (PlayerHandler.isOnline(Name))
				{
					messagePlayer(PlayerHandler.getPlayer(Name),Player, Message);
				}
				else
				{
					Player.sendMessage(StringUtil.formatColorCodes("&cUnable to send a message to &e" + Name + "&c, perhaps they're offline?"));
				}
			}
		}
	}

	private void messagePlayer(Player playerSendingTo, Player playerSendingFrom, String message)
	{
		playerSendingTo.sendMessage(ChatColor.WHITE + "[" + ChatColor.YELLOW + playerSendingFrom.getName() + ChatColor.AQUA + " -> " + ChatColor.GREEN + "You" + ChatColor.WHITE + "] " + message);
		playerSendingFrom.sendMessage(ChatColor.WHITE + "[" + ChatColor.YELLOW + "You" + ChatColor.AQUA + " -> " + ChatColor.GREEN + playerSendingTo.getName() + ChatColor.WHITE + "] " + message);
		ChatHandler.setRecentChatter(playerSendingTo.getName(), new ChatMessage(playerSendingFrom.getName(),playerSendingTo.getName()));
	}

	@CommandHandler(name = "r", usage = "/r <Message>"/*, permission = "tunnels.common.message"*/)
	public void quickRespondMessage(Player player, String[] commandArgs)
	{
		if (commandArgs.length > 0)
		{
			String playerName = player.getName();
			if (ChatHandler.hasRecentChatter(playerName))
			{
				String playerToSendMessageTo = ChatHandler.getRecentChatter(playerName);

				String Message = "";
				for (int I = 0; I < commandArgs.length; I++)
				{
					Message += commandArgs[I] + " ";
				}

				if (PlayerHandler.isOnline(playerToSendMessageTo))
				{
					Player playerSendingTo = PlayerHandler.getPlayer(playerToSendMessageTo);
					playerSendingTo.sendMessage(StringUtil.formatColorCodes("&r[&e" + player.getName() + "&b -> &aYou&r] " + Message));
					player.sendMessage(StringUtil.formatColorCodes("&r[&eYou&b -> &a" + playerSendingTo.getName()+ "&r] " + Message));
					ChatHandler.setRecentChatter(playerSendingTo.getName(), new ChatMessage(player.getName(),playerSendingTo.getName()));
				}
				else
				{
					player.sendMessage(StringUtil.formatColorCodes("&e" + playerToSendMessageTo + "&c has gone offline"));
				}
			}
			else
			{
				player.sendMessage(StringUtil.formatColorCodes("&cYou havn't received any messages from anybody"));
			}
		}
	}

	@CommandHandler(name = "m", usage = "/m <player> <Message>"/*, permission = "tunnels.common.message"*/)
	public void shortMessageCommand(Player player, String[] commandArgs)
	{
		if (commandArgs.length > 0 && !commandArgs[0].isEmpty())
		{
			String Name = commandArgs[0];
			if (!commandArgs[1].isEmpty())
			{
				String Message = "";
				for (int I = 1; I < commandArgs.length; I++)
				{
					Message += commandArgs[I] + " ";
				}

				if (PlayerHandler.isOnline(Name))
				{
					messagePlayer(PlayerHandler.getPlayer(Name),player, Message);
				}
				else
				{
					player.sendMessage(StringUtil.formatColorCodes("&cUnable to send a message to &e" + Name + "&c, perhaps they're offline?"));
				}
			}
		}
	}

	@CommandHandler(name = "tell", usage = "/tell <player> <message>"/*, permission = "tunnels.common.message"*/)
	public void tellMessageCommand(Player player, String[] commandArgs)
	{
		if (commandArgs.length > 0 && !commandArgs[0].isEmpty())
		{
			String Name = commandArgs[0];
			if (!commandArgs[1].isEmpty())
			{
				String Message = "";
				for (int I = 1; I < commandArgs.length; I++)
				{
					Message += commandArgs[I] + " ";
				}

				if (PlayerHandler.isOnline(Name))
				{
					messagePlayer(PlayerHandler.getPlayer(Name),player, Message);
				}
				else
				{
					player.sendMessage(StringUtil.formatColorCodes("&cUnable to send a message to &e" + Name + "&c, perhaps they're offline?"));
				}
			}
		}
	}

	@CommandHandler(name = "t", usage = "/t <player> <message>"/*, permission = "tunnels.common.message"*/)
	public void shortTellMessageCommand(Player player, String[] commandArgs)
	{
		if (commandArgs.length > 0 && !commandArgs[0].isEmpty())
		{
			String Name = commandArgs[0];
			if (!commandArgs[1].isEmpty())
			{
				String Message = "";
				for (int I = 1; I < commandArgs.length; I++)
				{
					Message += commandArgs[I] + " ";
				}

				if (PlayerHandler.isOnline(Name))
				{
					messagePlayer(PlayerHandler.getPlayer(Name),player, Message);
				}
				else
				{
					player.sendMessage(StringUtil.formatColorCodes("&cUnable to send a message to &e" + Name + "&c, perhaps they're offline?"));
				}
			}
		}
	}


	@CommandHandler(name = "whisper", usage = "/whisper <player> <Message>"/*, permission = "tunnels.common.message"*/)
	public void whisperCommand(Player player, String[] commandArgs)
	{
		if (commandArgs.length > 0 && !commandArgs[0].isEmpty())
		{
			String Name = commandArgs[0];
			if (!commandArgs[1].isEmpty())
			{
				String Message = "";
				for (int I = 1; I < commandArgs.length; I++)
				{
					Message += commandArgs[I] + " ";
				}

				if (PlayerHandler.isOnline(Name))
				{
					messagePlayer(PlayerHandler.getPlayer(Name),player, Message);
				}
				else
				{
					player.sendMessage(StringUtil.formatColorCodes("&cUnable to send a message to &e" + Name + "&c, perhaps they're offline?"));
				}
			}
		}
	}
}
