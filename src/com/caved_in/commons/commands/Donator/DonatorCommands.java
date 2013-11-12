package com.caved_in.commons.commands.Donator;

import com.caved_in.commons.handlers.Fireworks.FireworkEffectPlayer;
import com.caved_in.commons.handlers.Fireworks.FireworkSettings;
import com.caved_in.commons.handlers.Items.ItemType;
import com.caved_in.commons.commands.CommandController.CommandHandler;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DonatorCommands
{
	@CommandHandler
	(
			name = "hat",
			usage = "/hat <ItemID/ItemName> to place an item on your head (overwriting your current item); /hat to remove it",
			permission = "tunnels.common.hat"
	)
	public void HatCommand(Player Player, String[] Args)
	{
		Material HatItem = null;
		if (Args.length >= 1)
		{
			if (Args[0] != null)
			{
				if (StringUtils.isNumeric(Args[0]))
				{
					HatItem = Material.getMaterial(Integer.parseInt(Args[0]));
				}
				else
				{
					ItemType Type = ItemType.lookup(Args[0]);
					if (Type != null)
					{
						HatItem = Material.getMaterial(Type.getID());
					}
					else
					{
						Player.sendMessage(ChatColor.RED + "Please enter a valid Item Name");
						return;
					}
				}
			}
		}
		if (HatItem == null)
		{
			if (Player.getItemInHand() != null)
			{
				if (Player.getInventory().getHelmet() != null)
				{
					if (Player.getInventory().firstEmpty() != -1)
					{
						Player.getInventory().addItem(Player.getInventory().getHelmet());
						Player.getInventory().setHelmet(Player.getItemInHand());
						Player.getInventory().setItemInHand(null);
						Player.sendMessage(ChatColor.GREEN + "Enjoy your Helmet!");
						return;
					}
					else
					{
						Player.sendMessage(ChatColor.RED + "Please make sure you have room in your inventory for your current helmet before attempting to equip another");
						return;
					}
				}
				else
				{
					Player.getInventory().setHelmet(Player.getItemInHand());
					Player.getInventory().setItemInHand(null);
					Player.sendMessage(ChatColor.GREEN + "Enjoy your Helmet!");
					return;
				}
			}
			else
			{
				if (Player.getInventory().getHelmet() != null)
				{
					if (Player.getInventory().firstEmpty() != -1)
					{
						Player.getInventory().setItemInHand(Player.getInventory().getHelmet());
						Player.getInventory().setHelmet(null);
						Player.sendMessage(ChatColor.GREEN + "You've unequipped your helmet.");
						return;
					}
					else
					{
						Player.sendMessage(ChatColor.RED + "Please make sure you have room in your inventory for your current helmet before attempting to unequip it");
						return;
					}
				}
				else
				{
					Player.sendMessage(ChatColor.YELLOW + "The correct usage is /hat <ItemID/ItemName> to place an item on your head (overwriting your current item); /hat to remove it ");
					Player.sendMessage(ChatColor.YELLOW + "You're also able to use /hat if you've got an item you wish to equip in your hand, and space for your currently equipped one (if so)");
					return;
				}
			}
		}
		else
		{
			if (Player.getInventory().getHelmet() != null)
			{
				if (Player.getInventory().firstEmpty() != -1)
				{
					Player.getInventory().addItem(Player.getInventory().getHelmet());
					Player.getInventory().setHelmet(new ItemStack(HatItem, 1));
					Player.sendMessage(ChatColor.GREEN + "Enjoy your Helmet");
					return;
				}
				else
				{
					Player.sendMessage(ChatColor.RED + "Please make sure you have room in your inventory for your current helmet before attempting to unequip it");
					return;
				}
			}
			else
			{
				Player.getInventory().setHelmet(new ItemStack(HatItem, 1));
				Player.sendMessage(ChatColor.GREEN + "Enjoy your Helmet");
				return;
			}
		}
	}

	@CommandHandler
	(
			name = "fly",
			usage = "/fly to toggle your fly on and off accordingly",
			permission = "tunnels.common.fly"
	)
	public void FlyCommand(Player Player, String[] Args)
	{
		Player.setAllowFlight(!Player.getAllowFlight());
		Player.sendMessage(ChatColor.GREEN + "You are " + (Player.getAllowFlight() ? "now in fly mode" : "no longer in fly mode"));
	}

	@CommandHandler
	(
			name = "fw",
			usage = "/fw to create random fireworks around you",
			permission = "tunnels.common.fireworks"
	)
	public void FireworksCommand(Player Player, String[] Args)
	{
		try
		{
			new FireworkEffectPlayer().playFirework(Player.getWorld(), Player.getEyeLocation(), new FireworkSettings().randomFireworkEffect());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
