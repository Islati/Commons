package com.caved_in.commons.commands.Utility;

import com.caved_in.commons.Commons;
import com.caved_in.commons.handlers.Data.Disguises.Disguise;
import com.caved_in.commons.handlers.Items.ItemHandler;
import com.caved_in.commons.handlers.Player.PlayerHandler;
import com.caved_in.commons.handlers.Utilities.StringUtil;
import com.caved_in.commons.commands.CommandController.CommandHandler;
import com.caved_in.commons.handlers.Data.Menu.HelpScreen;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utility
{

	@CommandHandler
	(
			name = "gm",
			usage = "/gm <0/1/survival/creative> to switch gamemodes",
			permission = "tunnels.common.gamemode"
	)
	public void GamemodeHandler(Player Player, String[] Args)
	{
		if (Args.length >= 1 && Args[0] != null)
		{
			String Mode = Args[0];
			switch (Mode.toLowerCase())
			{
			case "0":
			case "s":
			case "survival":
				Player.setGameMode(GameMode.SURVIVAL);
				sendGameModeMessage(Player);
				break;
			case "1":
			case "creative":
			case "c":
				Player.setGameMode(GameMode.CREATIVE);
				sendGameModeMessage(Player);
				break;
			default:
				Player.sendMessage("Please enter a valid gamemode; Acceptable inputs are: 0/1/survival/creative/c/s");
				break;
			}

		}
		else
		{
			switch (Player.getGameMode())
			{
			case SURVIVAL:
				Player.setGameMode(GameMode.CREATIVE);
				break;
			case CREATIVE:
				Player.setGameMode(GameMode.SURVIVAL);
				break;
			default:
				Player.setGameMode(GameMode.SURVIVAL);
				break;
			}
			sendGameModeMessage(Player);
		}
	}

	@CommandHandler(name = "xp", usage = "/xp")
	public void playerXPCommand(Player player, String[] args)
	{
		player.sendMessage(StringUtil.formatColorCodes("&aYou have " + ((int)PlayerHandler.getData(player.getName()).getCurrency()) + " Tunnels XP"));
	}

	private HelpScreen getNickHelpScreen()
	{
		HelpScreen HelpScreen = new HelpScreen("Nickname Command Help");
		HelpScreen.setHeader(ChatColor.YELLOW + "<name> Page <page> of <maxpage>");
		HelpScreen.setFormat("<name> -- <desc>");
		HelpScreen.setFlipColor(ChatColor.GREEN, ChatColor.DARK_GREEN);

		HelpScreen.setEntry("/nick help", "Shows the help menu");
		HelpScreen.setEntry("/nick off [Player]", "Turns the nickname off for yourself, or another player");
		HelpScreen.setEntry("/nick <Name>", "Disguise yourself as another player");
		HelpScreen.setEntry("/nick <Player> <Name>", "Disguise another player");
		return HelpScreen;
	}

	@CommandHandler(name = "nicklist", permission = "tunnels.common.nicklist")
	public void getNickListCommand(Player player, String[] commandArgs)
	{
		HelpScreen HelpScreen = new HelpScreen("Currently Disguised Players");
		HelpScreen.setHeader(ChatColor.YELLOW + "<name> Page <page> of <maxpage>");
		HelpScreen.setFormat("<name> is disguised as <desc>");
		HelpScreen.setFlipColor(ChatColor.GREEN, ChatColor.DARK_GREEN);
		for (Disguise disguisedPlayer : Commons.disguiseDatabase.getDisguises())
		{
			HelpScreen.setEntry(disguisedPlayer.getPlayerDisguised(), disguisedPlayer.getDisguisedAs() + " on the server '" + disguisedPlayer.getServerOn() + "'");
		}

		if (commandArgs.length > 0)
		{
			int Page = Integer.parseInt(commandArgs[0]);
			HelpScreen.sendTo(player, Page, 7);
		}
		else
		{
			HelpScreen.sendTo(player, 1, 7);
		}
	}

	/*
	@CommandHandler(name = "nick", permission = "tunnels.common.nick")
	public void setNickNameCommand(Player player, String[] commandArgs)
	{
		// todo make /nick help menu
		if (commandArgs.length > 0)
		{
			if (commandArgs.length == 1 && commandArgs[0] != null && !commandArgs[0].isEmpty())
			{
				String nickSwitch = commandArgs[0];
				if (nickSwitch.equalsIgnoreCase("off"))
				{
					if (PlayerHandler.getData(player.getName()).hasNickName())
					{
						PlayerHandler.clearDisguise(player);
						Commons.disguiseDatabase.deletePlayerDisguiseData(player.getName());
					}
					else
					{
						player.sendMessage(StringUtil.formatColorCodes("&eYou're currently not disguised as anybody else"));
					}
				}
				else if (nickSwitch.equalsIgnoreCase("help") || nickSwitch.equalsIgnoreCase("?"))
				{
					this.getNickHelpScreen().sendTo(player, 1, 5);
				}
				else
				{
					PlayerHandler.setDisguise(player, nickSwitch);
					Commons.disguiseDatabase.addPlayerDisguiseData(new Disguise(player.getName(), nickSwitch, Commons.getConfiguration().getServerName()));
				}
			}
			else if (commandArgs.length > 1 && commandArgs[0] != null && !commandArgs[0].isEmpty())
			{
				String nickSwitch = commandArgs[0];
				if (nickSwitch.equalsIgnoreCase("off"))
				{
					if (commandArgs[1] != null && !commandArgs[1].isEmpty())
					{
						String playerOff = commandArgs[1];
						if (PlayerHandler.isOnline(playerOff))
						{
							Player playerToUnhide = Bukkit.getPlayer(playerOff);
							if (PlayerHandler.getData(playerToUnhide.getName()).hasNickName())
							{
								PlayerHandler.clearDisguise(playerToUnhide);
								Commons.disguiseDatabase.deletePlayerDisguiseData(playerToUnhide.getName());
							}
							else
							{
								player.sendMessage(StringUtil.formatColorCodes("&c" + playerOff + " &eisn't currently disguised as anybody else"));
							}
						}
						else
						{
							player.sendMessage(StringUtil.formatColorCodes("&c" + playerOff + "&e isn't currently online."));
						}
					}
				}
				else if (nickSwitch.equalsIgnoreCase("help") || nickSwitch.equalsIgnoreCase("?"))
				{
					if (commandArgs[1] != null && StringUtils.isNumeric(commandArgs[1]))
					{
						int Page = Integer.parseInt(commandArgs[1]);
						this.getNickHelpScreen().sendTo(player, Page, 5);
					}
				}
				else if (commandArgs[1] != null && !commandArgs[1].isEmpty())
				{
					String playerToHide = commandArgs[0];
					String playerDisguiseName = commandArgs[1];
					if (PlayerHandler.isOnline(playerToHide))
					{
						if (!PlayerHandler.isOnline(playerDisguiseName))
						{
							PlayerHandler.setDisguise(Bukkit.getPlayer(playerToHide), playerDisguiseName);
							Commons.disguiseDatabase.addPlayerDisguiseData(new Disguise(playerToHide, playerDisguiseName, Commons.getConfiguration().getServerName()));
						}
						else
						{
							player.sendMessage(StringUtil.formatColorCodes("&eYou can't disguise &c" + playerToHide + " &eas &c" + playerDisguiseName + "&e because &c" + playerDisguiseName + " &eis currently online."));
						}
					}
					else
					{
						player.sendMessage(StringUtil.formatColorCodes("&c" + playerToHide + " &eisn't online"));
					}
					// PlayerHandler.setDisguise(player, nickSwitch);
				}
			}
		}
		else
		{
			player.sendMessage(StringUtil.formatColorCodes("&cPlease use &e/nick help"));
		}
	}
	*/
	public void sendGameModeMessage(Player Player)
	{
		Player.sendMessage(ChatColor.GREEN + "You've switched your gamemode to " + WordUtils.capitalize(Player.getGameMode().name().toLowerCase()));
	}

	@CommandHandler(name = "skull", usage = "/skull <Name>", permission = "tunnels.common.skull")
	public void getPlayerSkullCommand(Player player,String[] commandArgs)
	{
		if (commandArgs.length > 0 && !commandArgs[0].isEmpty())
		{
			String playerName = commandArgs[0];
			ItemStack playerSkull = ItemHandler.getSkull(playerName);
			ItemHandler.setItemName(playerSkull,playerName + "'s Head");
			player.getInventory().addItem(playerSkull);
		}
		else
		{
			player.sendMessage(ChatColor.RED + "The proper usage is " + ChatColor.YELLOW + "/skull <Name>");
		}
	}

	@CommandHandler(name = "setspawn", usage = "/setspawn", permission = "tunnels.common.setspawn")
	public void setSpawnCommand(Player player, String[] commandArgs)
	{
		Location playerLocation = player.getLocation();
		int x = (int)playerLocation.getX();
		int y = (int)playerLocation.getY();
		int z = (int)playerLocation.getZ();
		player.getWorld().setSpawnLocation(x,y,z);
		player.sendMessage(ChatColor.GREEN + "Spawn location has been set!");
	}

}
