package com.caved_in.commons.items;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class ItemHandler
{
	/**
	 * Get the lore of an item if it has lore
	 * 
	 * @param Item
	 *            Item to get lore of
	 * @return ArrayList<String> of the lore if the item has lore, otherwise
	 *         null
	 */
	public static ArrayList<String> getItemLore(ItemStack Item)
	{
		ArrayList<String> ReturnLore = new ArrayList<String>();
		if (Item.hasItemMeta() && Item.getItemMeta().hasLore())
		{
			for (String S : Item.getItemMeta().getLore())
			{
				ReturnLore.add(S);
			}
			return ReturnLore;
		}
		return null;
	}

	/**
	 * Check the players inventory for an item with a specific material and name
	 * Uses a fuzzy search to determine if the item is in their inventory
	 * @param player
	 * @param material
	 * @param name
	 * @return true if they have the item, false otherwise
	 */
	public static boolean playerHasItem(Player player, Material material, String name)
	{
		HashMap<Integer,? extends ItemStack> playerInventoryItems = player.getInventory().all(material);
		for(Entry<Integer, ? extends ItemStack> inventoryItem : playerInventoryItems.entrySet())
		{
			if (itemNameContains(inventoryItem.getValue(),name))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Get lore of item at specific line
	 * 
	 * @param Item
	 *            Item to get lore of
	 * @param Line
	 *            Index of lore to get
	 * @return String of lore if it exists, otherwise null
	 */
	public static String getItemLore(ItemStack Item, int Line)
	{
		if (Item.hasItemMeta() && Item.getItemMeta().hasLore())
		{
			try
			{
				return Item.getItemMeta().getLore().get(Line);
			}
			catch (IndexOutOfBoundsException Exception)
			{
				Exception.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public static ItemStack addLore(ItemStack Item, List<String> Lore)
	{
		ItemMeta iMeta = Item.getItemMeta();
		List<String> LoreLine = new ArrayList<String>();
		if (Item.hasItemMeta() && Item.getItemMeta().hasLore())
		{
			LoreLine = Item.getItemMeta().getLore();
		}
		LoreLine.addAll(Lore);
		iMeta.setLore(LoreLine);
		Item.setItemMeta(iMeta);
		return Item;
	}

	/**
	 * Checks if an items lore contains specific text
	 * 
	 * @param Item
	 *            Item to check
	 * @param Text
	 *            Text to check the item for
	 * @return true if the item has the text in its lore, otherwise false.
	 */
	public static boolean itemLoreContains(ItemStack Item, String Text)
	{
		if (Item.hasItemMeta() && Item.getItemMeta().hasLore())
		{
			ArrayList<String> Lore = getItemLore(Item);
			for (String s : Lore)
			{
				if (s.toLowerCase().contains(Text.toLowerCase()))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Set the name of an Item
	 * 
	 * @param Item
	 *            Item to set the name of
	 * @param Text
	 *            The name to give the item
	 */
	public static void setItemName(ItemStack Item, String Text)
	{
		ItemMeta iMeta = Item.getItemMeta();
		iMeta.setDisplayName(Text);
		Item.setItemMeta(iMeta);
	}

	/**
	 * Get the name of an item
	 * 
	 * @param Item
	 * @return Name of the item if it has a name, otherwise its material type in
	 *         lowercase
	 */
	public static String getItemName(ItemStack Item)
	{
		if (Item.hasItemMeta() && Item.getItemMeta().hasDisplayName())
		{
			return Item.getItemMeta().getDisplayName();
		}
		return Item.getType().toString().toLowerCase();
	}

	/**
	 * Checks if an items name contains the given text
	 * @param item
	 * @param text
	 * @return
	 */
	public static boolean itemNameContains(ItemStack item, String text)
	{
		return (getItemName(item).toLowerCase().contains(text.toLowerCase()));
	}

	/**
	 * Removes an amount of items from a stack
	 * 
	 * @param Item
	 * @param Amount
	 * @return The itemstack if there are more than 0 items left in the stack,
	 *         otherwise null
	 */
	public static ItemStack removeFromStack(ItemStack Item, int Amount)
	{
		if (Item.getAmount() > Amount)
		{
			ItemMeta StackMeta = Item.getItemMeta();
			ItemStack Return = new ItemStack(Item.getType(), Item.getAmount() - Amount);
			Return.setItemMeta(StackMeta);
			Return.setDurability(Item.getDurability());
			return Return;
		}
		return null;
	}

	/**
	 * Set the color to a piece of leather armor
	 * 
	 * @param Item
	 * @param Color
	 * @return true if the color was set, otherwise false
	 */
	public static boolean setColor(ItemStack Item, Color Color)
	{
		List<Material> Leathers = Arrays.asList(Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS);
		if (Leathers.contains(Item.getType()))
		{
			try
			{
				LeatherArmorMeta ItemMeta = (LeatherArmorMeta) Item.getItemMeta();
				ItemMeta.setColor(Color);
				Item.setItemMeta(ItemMeta);
			}
			catch (Exception Ex)
			{
				Ex.printStackTrace();
				return false;
			}
		}
		return false;
	}

	/**
	 * Add an enchantment to an item ignoring the restrictions
	 * 
	 * @param Item
	 * @param Enchant
	 * @param Level
	 * @param IgnoreRestrictions
	 * @return true if the enchantment was added, false otherwise
	 */
	public static boolean addEnchantment(ItemStack Item, Enchantment Enchant, int Level, boolean IgnoreRestrictions)
	{
		ItemMeta iMeta = Item.getItemMeta();
		boolean Status = iMeta.addEnchant(Enchant, Level, IgnoreRestrictions);
		Item.setItemMeta(iMeta);
		return Status;
	}

	/**
	 * Calls addEnchantment but follows restrictions
	 * 
	 * @param Item
	 * @param Enchant
	 * @param Level
	 * @return
	 */
	public static boolean addEnchantment(ItemStack Item, Enchantment Enchant, int Level)
	{
		return addEnchantment(Item, Enchant, Level, false);
	}

	/**
	 * Sets the lore of an item
	 * 
	 * @param Item
	 * @param Lore
	 * @return true if the lore was changed, false otherwise.
	 */
	public static boolean setItemLore(ItemStack Item, List<String> Lore)
	{
		try
		{
			ItemMeta iMeta = Item.getItemMeta();
			iMeta.setLore(Lore);
			Item.setItemMeta(iMeta);
			return true;
		}
		catch (Exception Ex)
		{
			Ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Make an itemstack of the given type
	 * 
	 * @param Material
	 * @return
	 */
	public static ItemStack makeItemStack(Material Material)
	{
		return new ItemStack(Material);
	}

	/**
	 * Make an itemstack of the given type, with the specified name
	 * 
	 * @param Material
	 * @param Name
	 * @return
	 */
	public static ItemStack makeItemStack(Material Material, String Name)
	{
		ItemStack Item = new ItemStack(Material);
		setItemName(Item, Name);
		return Item;
	}

	/**
	 * 
	 * @param Material
	 * @param Name
	 * @param Lore
	 * @return
	 */
	public static ItemStack makeItemStack(Material Material, String Name, List<String> Lore)
	{
		ItemStack Item = new ItemStack(Material);
		setItemName(Item, Name);
		setItemLore(Item, Lore);
		return Item;
	}

	/**
	 * Make a leather item dyed with the given color, name, lore, and
	 * enchantments
	 * 
	 * @param Material
	 * @param Color
	 * @return
	 */
	public static ItemStack makeLeatherItemStack(Material Material, String Name, List<String> Lore, HashMap<Enchantment, Integer> Enchantments, Color Color)
	{
		ItemStack Item = new ItemStack(Material);
		setItemName(Item, Name);
		setItemLore(Item, Lore);
		for (Entry<Enchantment, Integer> Enchant : Enchantments.entrySet())
		{
			addEnchantment(Item, Enchant.getKey(), Enchant.getValue(), true);
		}
		setColor(Item, Color);
		return Item;
	}

	/**
	 * Make a leather item dyed with the given color, name, and lore
	 * 
	 * @param Material
	 * @param Color
	 * @return
	 */
	public static ItemStack makeLeatherItemStack(Material Material, String Name, List<String> Lore, Color Color)
	{
		ItemStack Item = new ItemStack(Material);
		setItemName(Item, Name);
		setItemLore(Item, Lore);
		setColor(Item, Color);
		return Item;
	}

	public static ItemStack getSkull(String Player)
	{
		ItemStack skullStack = new MaterialData(Material.SKULL_ITEM,(byte)3).toItemStack();
		SkullMeta skullMeta = (SkullMeta)skullStack.getItemMeta();
		skullMeta.setOwner(Player);
		skullStack.setItemMeta(skullMeta);
		return skullStack;
	}

	public static ItemStack getSkull(String playerName, String itemName)
	{
		ItemStack skull = getSkull(playerName);
		setItemName(skull, itemName);
		return skull;
	}

	/**
	 * Make a leather item dyed with the given color
	 * 
	 * @param Material
	 * @param Color
	 * @return
	 */
	public static ItemStack makeLeatherItemStack(Material Material, Color Color)
	{
		ItemStack Item = new ItemStack(Material);
		setColor(Item, Color);
		return Item;
	}

	/**
	 * Format a materials name to a properly capitilized and spaced string
	 * 
	 * @param Material
	 * @return
	 */
	public static String getFormattedMaterialName(Material Material)
	{
		return WordUtils.capitalize(Material.name().toLowerCase().replaceAll("_", " "));
	}

	/**
	 * Revert the formatting on a string to retrieve its relative material
	 * 
	 * @param string
	 * @return
	 */
	public static Material unFormatMaterialName(String string)
	{
		return Material.valueOf(string.toUpperCase().replaceAll(" ", "_"));
	}

	/**
	 * Get Dye of the given Color
	 * 
	 * @param Color
	 * @return
	 */
	public static Dye getDye(DyeColor Color)
	{
		Dye Dye = new Dye();
		Dye.setColor(Color);
		return Dye;
	}

	/**
	 * Returns a materialData instance based on a string formatted to id:datavalue
	 * @param idDatavalue
	 * @return
	 */
	public static MaterialData getMaterialDataFromString(String idDatavalue)
	{
		MaterialData materialData;
		if (idDatavalue.contains(":"))
		{
			String[] materialSplit = idDatavalue.split(":");
			int materialId = Integer.parseInt(materialSplit[0]);
			int dataValue = Integer.parseInt(materialSplit[1]);
			materialData = new MaterialData(Material.getMaterial(materialId),(byte)dataValue);
		}
		else
		{
			materialData = new MaterialData(Material.getMaterial(Integer.parseInt(idDatavalue)));
		}
		return materialData;
	}

	public static MaterialData getMaterialData(int itemID, int dataValue)
	{
		return new MaterialData(Material.getMaterial(itemID),(byte)dataValue);
	}

	public static MaterialData getMaterialData(Material material, int dataValue)
	{
		return new MaterialData(material,(byte)dataValue);
	}

}