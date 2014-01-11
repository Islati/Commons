package com.caved_in.commons.commands.utility;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.commands.CommandController.CommandHandler;
import com.caved_in.commons.disguises.Disguise;
import com.caved_in.commons.entity.EntityUtility;
import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.items.ItemType;
import com.caved_in.commons.menu.HelpScreen;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.world.WorldHandler;
import com.caved_in.commons.world.WorldTime;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class UtilityCommands {

	@CommandHandler(name = "gm", usage = "/gm <0/1/survival/creative> to switch gamemodes", permission = "tunnels.common.gamemode")
	public void GamemodeHandler(Player player, String[] args) {
		if (args.length >= 1) {
			String modeArgument = args[0];
			switch (modeArgument.toLowerCase()) {
				case "0":
				case "s":
				case "survival":
					player.setGameMode(GameMode.SURVIVAL);
					break;
				case "1":
				case "creative":
				case "c":
					player.setGameMode(GameMode.CREATIVE);
					break;
				case "a":
				case "adventure":
				case "2":
					player.setGameMode(GameMode.ADVENTURE);
					break;
				default:
					PlayerHandler.sendMessage(player, Messages.INVALID_COMMAND_USAGE("gamemode"));
					return;
			}

		} else {
			switch (player.getGameMode()) {
				case SURVIVAL:
					player.setGameMode(GameMode.CREATIVE);
					break;
				case CREATIVE:
					player.setGameMode(GameMode.ADVENTURE);
					break;
				case ADVENTURE:
					player.setGameMode(GameMode.SURVIVAL);
					break;
				default:
					player.setGameMode(GameMode.SURVIVAL);
					break;
			}
		}
		sendGameModeMessage(player);

	}

	@CommandHandler(name = "xp", usage = "/xp")
	public void playerXPCommand(Player player, String[] args) {
		PlayerHandler.sendMessage(player, Messages.TUNNELS_XP_BALANCE(player));
	}

	@CommandHandler(name = "nicklist", permission = "tunnels.common.nicklist")
	public void getNickListCommand(Player player, String[] commandArgs) {
		HelpScreen HelpScreen = new HelpScreen("Currently Disguised Players");
		HelpScreen.setHeader(ChatColor.YELLOW + "<name> Page <page> of <maxpage>");
		HelpScreen.setFormat("<name> is disguised as <desc>");
		HelpScreen.setFlipColor(ChatColor.GREEN, ChatColor.DARK_GREEN);
		for (Disguise disguisedPlayer : Commons.disguiseDatabase.getDisguises()) {
			HelpScreen.setEntry(disguisedPlayer.getPlayerDisguised(), disguisedPlayer.getDisguisedAs() + " on the server '" + disguisedPlayer.getServerOn() + "'");
		}

		if (commandArgs.length > 0) {
			int Page = Integer.parseInt(commandArgs[0]);
			HelpScreen.sendTo(player, Page, 7);
		} else {
			HelpScreen.sendTo(player, 1, 7);
		}
	}

	public void sendGameModeMessage(Player Player) {
		Player.sendMessage(ChatColor.GREEN + "You've switched your gamemode to " + WordUtils.capitalize(Player.getGameMode().name().toLowerCase()));
	}

	@CommandHandler(name = "skull", usage = "/skull <Name>", permission = "tunnels.common.skull")
	public void getPlayerSkullCommand(Player player, String[] commandArgs) {
		if (commandArgs.length > 0) {
			String playerName = commandArgs[0];
			ItemStack playerSkull = ItemHandler.getSkull(playerName);
			ItemHandler.setItemName(playerSkull, playerName + "'s Head");
			player.getInventory().addItem(playerSkull);
		} else {
			PlayerHandler.sendMessage(player, Messages.INVALID_COMMAND_USAGE("name"));
		}
	}

	@CommandHandler(name = "setspawn", usage = "/setspawn", permission = "tunnels.common.setspawn")
	public void setSpawnCommand(Player player, String[] commandArgs) {
		if (WorldHandler.setSpawn(player.getWorld(), player.getLocation())) {
			PlayerHandler.sendMessage(player, "&aSpawn location for the world &7" + player.getWorld().getName() + "&a has been set!");
		} else {
			PlayerHandler.sendMessage(player,"&eThere was an error changing the spawn location for world &7" + player.getWorld().getName() + "&e; please check the console.");
		}
	}

	@CommandHandler(name = "more", usage = "/more", permission = "tunnels.common.more")
	public void onMoreCommand(Player player, String[] commandArgs) {
		ItemStack playerHandItem = player.getItemInHand();
		playerHandItem.setAmount(playerHandItem.getMaxStackSize());
		player.setItemInHand(playerHandItem);
	}

	@CommandHandler(name = "heal", usage = "/heal", permission = "tunnels.common.heal")
	public void onHealCommand(Player player, String[] commandArgs) {
		PlayerHandler.removePotionEffects(player);
		EntityUtility.setCurrentHealth(player,EntityUtility.getMaxHealth(player));
		PlayerHandler.sendMessage(player, Messages.PLAYER_HEALED);
	}

	@CommandHandler(name = "ci", usage = "/ci [player]", permission = "tunnels.common.clearinventory", aliases = {"clearinventory", "clearinv"})
	public void onClearInventoryCommand(CommandSender commandSender, String[] args) {
		//Check if we've got a player using this command
		if (args.length > 0) {
			String playerName = args[1];
			//Check if there's a player online with the name in our argument
			if (PlayerHandler.isOnline(playerName)) {
				//Get the player and clear their inventory + armor
				Player player = PlayerHandler.getPlayer(playerName);
				PlayerHandler.clearInventory(player,true);
				player.sendMessage(Messages.INVENTORY_CLEARED);
			} else {
				PlayerHandler.sendMessage(commandSender, Messages.PLAYER_OFFLINE("name"));
			}
		} else {
			if (commandSender instanceof Player) {
				Player player = (Player)commandSender;
				PlayerHandler.clearInventory(player);
				PlayerHandler.sendMessage(player,Messages.INVENTORY_CLEARED);
			} else {
				PlayerHandler.sendMessage(commandSender, Messages.INVALID_COMMAND_USAGE("name"));
			}
		}
	}

	@CommandHandler(name = "tphere", permission = "tunnels.common.tphere", aliases = {"bring"})
	public void onTeleportHereCommand(Player player, String[] args) {
		if (args.length > 0) {
			String playerName = args[0];
			if (PlayerHandler.isOnline(playerName)) {
				Player playerToTeleport = PlayerHandler.getPlayer(playerName);
				playerToTeleport.teleport(player, PlayerTeleportEvent.TeleportCause.COMMAND);
			} else {
				PlayerHandler.sendMessage(player,Messages.PLAYER_OFFLINE("name"));
			}
		} else {
			PlayerHandler.sendMessage(player,Messages.INVALID_COMMAND_USAGE("name"));
		}
	}

	//TODO Make this command able to teleport one player to another via arguments; CommandSender instead of player
	@CommandHandler(name = "tp", permission = "tunnels.common.teleport", aliases = {"teleport"})
	public void onTeleportCommand(CommandSender sender, String[] args) {
		//Check if the command issuer only entered one name
		if (args.length == 1) {
			//Check if the issuer is a player
			if (sender instanceof Player) {
				Player player = (Player)sender;
				String playerName = args[0];
				//Check if the player requested is online
				if (PlayerHandler.isOnline(playerName)) {
					//Teleport the player using the command to the player they're requesting
					player.teleport(PlayerHandler.getPlayer(playerName), PlayerTeleportEvent.TeleportCause.COMMAND);
				} else {
					//Player is offline, send the message saying so
					PlayerHandler.sendMessage(player, Messages.PLAYER_OFFLINE(playerName));
				}
			} else {
				PlayerHandler.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("player", "target"));
			}
		} else if (args.length == 2) {
			String playerOne = args[0];
			String playerTwo = args[1];
			//If player one is online
			if (PlayerHandler.isOnline(playerOne)) {
				//If player two is online
				if (PlayerHandler.isOnline(playerTwo)) {
					//Teleport the first player to the second
					PlayerHandler.teleport(PlayerHandler.getPlayer(playerOne),PlayerHandler.getPlayer(playerTwo));
					PlayerHandler.sendMessage(sender, Messages.TELEPORTED_TO(playerOne, playerTwo));
				} else {
					PlayerHandler.sendMessage(sender, Messages.PLAYER_OFFLINE(playerTwo));
				}

			} else {
				PlayerHandler.sendMessage(sender, Messages.PLAYER_OFFLINE(playerOne));
			}
		}
	}

	@CommandHandler(name = "i", permission = "tunnels.common.item", aliases = {"item"})
	public void onItemCommand(Player player, String[] args) {
		if (args.length > 0) {
			//Our item argument
			String itemArgument = args[0];
			//Our item ID
			int itemID = 0;
			//How many of the item we want
			int itemAmount = 1;
			int dataValue = 0;
			//Check if they want an item with a byte value or not
			if (itemArgument.contains(":")) {
				String[] splitItemData = itemArgument.split(":");
				//The ID or material
				String itemMaterial = splitItemData[0];
				//The potential byte value
				String itemByte = splitItemData[1];

				//Check if the first have of our string is an integer, or a name
				if (StringUtils.isNumeric(itemMaterial)) {
					itemID = Integer.parseInt(itemMaterial);
				} else {
					//Get the item type based on whatever the player entered
					ItemType itemType = ItemType.lookup(itemMaterial, true);
					if (itemType != null) {
						//Set our itemID to be the id of the name they entered
						itemID = itemType.getID();
					} else {
						//Player entered an item that doesn't exist
						PlayerHandler.sendMessage(player, Messages.ITEM_DOESNT_EXIST(itemMaterial));
						return;
					}
				}
				//Now we parse for the extra byte data
				if (StringUtils.isNumeric(itemByte)) {
					//Parse the byte and say that we DO have a byte value
					dataValue = Integer.parseInt(itemByte);
				} else {
					//They entered an invalid data value
					PlayerHandler.sendMessage(player, Messages.INVALID_ITEM_DATA(itemByte));
					return;
				}
			} else {
				//Check if they entered an ID or name
				if (StringUtils.isNumeric(itemArgument)) {
					//They passed an ID as the argument, so parse it
					itemID = Integer.parseInt(itemArgument);
				} else {
					//Look for the itemtype based on the name they entered
					ItemType itemType = ItemType.lookup(itemArgument,true);
					if (itemType != null) {
						itemID = itemType.getID();
					} else {
						//No results; Sawwy!
						PlayerHandler.sendMessage(player,Messages.ITEM_DOESNT_EXIST(itemArgument));
					}
				}
			}

			//Now we check if the player entered an amount that they'd like.
			if (args.length > 1) {
				String itemAmountArg = args[1];
				if (StringUtils.isNumeric(itemAmountArg)) {
					//Parse what the player entered and set that to the amount to give them
					itemAmount = Integer.parseInt(itemAmountArg);
				} else {
					PlayerHandler.sendMessage(player, Messages.INVALID_COMMAND_USAGE("item", "item amount"));
				}
			}

			//Now we create the itemstack based on what they gave us previously.
			ItemStack itemStack = ItemHandler.getMaterialData(itemID, dataValue).toItemStack(itemAmount);
			//give the player the item :)
			PlayerHandler.giveItem(player, itemStack);
		} else {
			PlayerHandler.sendMessage(player,Messages.INVALID_COMMAND_USAGE("item"));
		}
	}

	@CommandHandler(name = "speed", permission = "tunnels.common.speed")
	public void onSpeedCommand(Player player, String[] args) {
		PlayerWrapper playerWrapper = PlayerHandler.getData(player);
		if (args.length > 0) {
			String speedArg = args[0];
			if (StringUtils.isNumeric(speedArg)) {
				//Get the speed from whatever the player passed as an argument
				double speed = Double.parseDouble(speedArg);
				//Assure the number isn't >10 or <0.1
				if (speed >= 10) {
					speed = 10;
				} else if (speed <= 0.01) {
					speed = 0.01;
				}

				//If they're flying, set their fly speed; if not, their walk speed
				if (player.isFlying()) {
					playerWrapper.setFlySpeed(speed);
				} else {
					playerWrapper.setWalkSpeed(speed);
				}

				//Send the player a message saying their speed was updated
				PlayerHandler.sendMessage(player, Messages.SPEED_UPDATED(player.isFlying(), speed));

			} else {
				PlayerHandler.sendMessage(player, Messages.INVALID_COMMAND_USAGE("speed"));
			}
		} else {
			//They didn't pass a speed argument, so reset their speeds to default
			if (player.isFlying()) {
				//Default fly speed
				playerWrapper.setFlySpeed(PlayerWrapper.defaultFlySpeed);
			} else {
				//Default walk speed
				playerWrapper.setWalkSpeed(PlayerWrapper.defaultWalkSpeed);
			}
			PlayerHandler.sendMessage(player, Messages.SPEED_RESET(player.isFlying()));
		}
	}

	@CommandHandler(name = "time", permission = "tunnels.commons.time")
	public void onTimeCommand(CommandSender sender, String[] args) {
		if (args.length > 0) {
			String timeAction = args[0];
			World world;
			if (sender instanceof Player) {
				world = ((Player)sender).getWorld();
			} else {
				//Not a player sending the message, so check if they passed an argument for a world
				if (args.length > 1) {
					//Get what they passed, and see if the world exists
					String worldName = args[1];
					if (WorldHandler.worldExists(worldName)) {
						world = WorldHandler.getWorld(worldName);
					} else {
						//Send a message saying the world requested doesn't exist
						PlayerHandler.sendMessage(sender, Messages.WORLD_DOESNT_EXIST(worldName));
						return;
					}
				} else {
					//They didnt include a world argument
					PlayerHandler.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("time", "world"));
					return;
				}
			}

			timeAction = timeAction.toLowerCase();

			//Switch on what the player entered
			switch (timeAction) {
				case "day":
				case "night":
				case "dawn":
					WorldHandler.setTime(world, WorldTime.getWorldTime(timeAction));
					PlayerHandler.sendMessage(sender, Messages.TIME_CHANGED(world.getName(), timeAction));
					break;
				default:
					PlayerHandler.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("Time [day/night/dawn]"));
					break;
			}

		} else {
			PlayerHandler.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("Time [day/night/dawn]"));
		}
	}

	@CommandHandler(name = "day", permission = "tunnels.commons.time")
	public void onDayCommand(Player player, String[] args) {
		World world = player.getWorld();
		WorldHandler.setTimeDay(world);
		PlayerHandler.sendMessage(player, Messages.TIME_CHANGED(world.getName(), "day"));
	}

	@CommandHandler(name = "night", permission = "tunnels.common.time")
	public void onNightCommand(Player player, String[] args) {
		World world = player.getWorld();
		WorldHandler.setTimeNight(world);
		PlayerHandler.sendMessage(player, Messages.TIME_CHANGED(world.getName(), "night"));
	}

}
