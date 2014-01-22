package com.caved_in.commons.commands.utility;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.commands.CommandController.CommandHandler;
import com.caved_in.commons.disguises.Disguise;
import com.caved_in.commons.entity.EntityUtility;
import com.caved_in.commons.items.Enchantments;
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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class UtilityCommands {

	@CommandHandler(name = "gm", usage = "/gm <0/1/survival/creative> to switch gamemodes", permission = "tunnels.common.gamemode")
	public void GamemodeHandler(Player player, String[] commandArgs) {
		if (commandArgs.length >= 1 && commandArgs[0] != null) {
			String modeArgument = commandArgs[0];
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
			sendGameModeMessage(player);
		}
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
			PlayerHandler.sendMessage(player, "&eThere was an error changing the spawn location for world &7" + player.getWorld().getName() + "&e; please check the console.");
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
		EntityUtility.setCurrentHealth(player, EntityUtility.getMaxHealth(player));
		player.sendMessage("&eYou've been healed!");
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
				PlayerHandler.clearInventory(player, true);
				player.sendMessage(Messages.INVENTORY_CLEARED);
			} else {
				PlayerHandler.sendMessage(commandSender, Messages.PLAYER_OFFLINE("name"));
			}
		} else {
			if (commandSender instanceof Player) {
				Player player = (Player) commandSender;
				PlayerHandler.clearInventory(player);
				PlayerHandler.sendMessage(player, Messages.INVENTORY_CLEARED);
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
				PlayerHandler.sendMessage(player, Messages.PLAYER_OFFLINE("name"));
			}
		} else {
			PlayerHandler.sendMessage(player, Messages.INVALID_COMMAND_USAGE("name"));
		}
	}

	//TODO Make this command able to teleport one player to another via arguments; CommandSender instead of player
	@CommandHandler(name = "tp", permission = "tunnels.common.teleport", aliases = {"teleport"})
	public void onTeleportCommand(CommandSender sender, String[] args) {
		//Check if the command issuer only entered one name
		if (args.length == 1) {
			//Check if the issuer is a player
			if (sender instanceof Player) {
				Player player = (Player) sender;
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
					PlayerHandler.teleport(PlayerHandler.getPlayer(playerOne), PlayerHandler.getPlayer(playerTwo));
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
					ItemType itemType = ItemType.lookup(itemArgument, true);
					if (itemType != null) {
						itemID = itemType.getID();
					} else {
						//No results; Sawwy!
						PlayerHandler.sendMessage(player, Messages.ITEM_DOESNT_EXIST(itemArgument));
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
			PlayerHandler.sendMessage(player, Messages.ITEM_GIVEN_COMMAND(ItemHandler.getFormattedMaterialName(itemStack.getType()), itemAmount));
		} else {
			PlayerHandler.sendMessage(player, Messages.INVALID_COMMAND_USAGE("item"));
		}
	}

	@CommandHandler(name = "speed", permission = "tunnels.common.speed")
	public void onSpeedCommand(Player player, String[] args) {
		PlayerWrapper playerWrapper = PlayerHandler.getData(player);
		if (args.length > 0) {
			String speedArg = args[0];
			if (StringUtils.isNumeric(speedArg)) {
				//Get the speed from whatever the player passed as an argument
				double speed = Integer.parseInt(speedArg);
				//Assure the number isn't >8 or <0.1
				if (speed >= 7.8) {
					speed = 7.8;
				} else if (speed <= 1) {
					speed = 1;
				}

				//If they're flying, set their fly speed; if not, their walk speed
				if (player.isFlying()) {
					double fSpeed = (speed + (PlayerWrapper.defaultWalkSpeed * 10)) / 10;
					playerWrapper.setFlySpeed(fSpeed);
					PlayerHandler.sendMessage(player, "" + player.getFlySpeed());

				} else {
					double fSpeed = (speed + (PlayerWrapper.defaultWalkSpeed * 10)) / 10;
					playerWrapper.setWalkSpeed(fSpeed);
					PlayerHandler.sendMessage(player, "" + player.getWalkSpeed());
				}

				//Send the player a message saying their speed was updated
				PlayerHandler.sendMessage(player, Messages.SPEED_UPDATED(player.isFlying(), Integer.parseInt(speedArg)));

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
				world = ((Player) sender).getWorld();
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

	@CommandHandler(name = "enchant", permission = "tunnels.common.enchant")
	public void onEnchantCommand(Player player, String[] args) {
		//Check if an enchantment name was passed
		if (args.length > 0) {
			String enchantmentName = args[0];
			//Check if the enchantment they entered is a valid enchantment
			if (Enchantments.isEnchantment(enchantmentName)) {
				//Get the enchantment they entered
				Enchantment enchantment = Enchantments.getEnchantment(enchantmentName);
				int enchantmentLevel = 1;
				//Check if they entered a level argument for the enchantment
				if (args.length > 1 && StringUtils.isNumeric(args[1])) {
					enchantmentLevel = Integer.parseInt(args[1]);
				}
				ItemStack itemStack = player.getItemInHand();
				//If the player has an item in their hand
				if (itemStack != null) {
					//Check if they enchanted the item successfully, and if so send them a message
					if (ItemHandler.addEnchantment(itemStack, enchantment, enchantmentLevel, true)) {
						PlayerHandler.sendMessage(player, Messages.ITEM_ENCHANTED(enchantmentName, enchantmentLevel));
					} else {
						//Send them a message saying it failed
						PlayerHandler.sendMessage(player, Messages.FAILED_TO_ENCHANT_ITEM);
					}
				} else {
					//Send the player a message saying they require an item in their hand
					PlayerHandler.sendMessage(player, Messages.ITEM_IN_HAND_REQUIRED);
				}
			} else {
				//Send the player a message saying the enchantment doesn't exist
				PlayerHandler.sendMessage(player, Messages.ENCHANTMENT_DOESNT_EXIST(enchantmentName));
			}
		} else {
			PlayerHandler.sendMessage(player, Messages.INVALID_COMMAND_USAGE("enchantment", "level"));
		}
	}

	@CommandHandler(name = "feed", permission = "tunnels.common.feed")
	public void onFeedCommand(CommandSender sender, String[] args) {
		//Check if they entered a player name as an argument
		if (args.length > 0) {
			String playerName = args[0];
			//Check if the player name they entered is online
			if (PlayerHandler.isOnline(playerName)) {
				//Get the player and feed them
				Player player = PlayerHandler.getPlayer(playerName);
				PlayerHandler.feedPlayer(player);
				//Send messages saying the player requested was fed
				PlayerHandler.sendMessage(player, Messages.PLAYER_FED);
				PlayerHandler.sendMessage(sender, Messages.PLAYER_FED(playerName));
			} else {
				PlayerHandler.sendMessage(sender, Messages.PLAYER_OFFLINE(playerName));
			}
		} else {
			if (sender instanceof Player) {
				PlayerHandler.feedPlayer((Player)sender);
				PlayerHandler.sendMessage(sender,Messages.PLAYER_FED);
			} else {
				PlayerHandler.sendMessage(sender,Messages.PLAYER_COMMAND_SENDER_REQUIRED);
			}
		}
	}

	public void onItemRepairCommand(Player player, String[] args) {
		//Todo find how to make a /repair command
	}

	public void onPowerMineCommand(Player player, String[] args) {
		//Toggling power-mine
		//power mine = hold right click and destroy block thats are right clicked
	}

	//Make features to detroy/modify a cube of blocks, spehere, how long (depth & width)
	//kinda like world edit but not really

}
