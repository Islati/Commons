package com.caved_in.commons.items;

import com.caved_in.commons.Messages;
import com.caved_in.commons.block.BlockHandler;
import com.caved_in.commons.inventory.InventoryHandler;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.reflection.ReflectionUtilities;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;

import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

public class ItemHandler {

	private static final Method TO_NMS = ReflectionUtilities.getMethod(ReflectionUtilities.getCBClass("inventory.CraftItemStack"), "asNMSCopy", ItemStack.class);

	public static Object toNMS(ItemStack stack) {
		return ReflectionUtilities.invokeMethod(TO_NMS, null, stack);
	}

	/**
	 * Check whether or not an item has metadata
	 *
	 * @param itemStack
	 * @return true if the item has metadata, false otherwise
	 */
	public static boolean itemHasMetadata(ItemStack itemStack) {
		return itemStack.hasItemMeta();
	}

	/**
	 * Get the metadata attached to an itemstack
	 *
	 * @param itemStack
	 * @return ItemMeta of the item if it exists, otherwise null.
	 */
	public static ItemMeta getItemMeta(ItemStack itemStack) {
		return itemStack.getItemMeta();
	}

	/**
	 * Set the MetaData on an Item Stack
	 *
	 * @param itemStack item stack to set the metadata on
	 * @param itemMeta  The metadata to set on our item
	 * @return The itemstack passed, but with its metadata changed.
	 */
	public static ItemStack setItemMeta(ItemStack itemStack, ItemMeta itemMeta) {
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	/**
	 * Get the lore of an item if it has lore
	 *
	 * @param itemStack Item to get lore of
	 * @return List<String> of the lore if the item has lore, otherwise
	 * null
	 */
	public static List<String> getItemLore(ItemStack itemStack) {
		//Check if our item has metadata, and that metadata has lore
		if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
			//Our item has lore; So return it!
			return itemStack.getItemMeta().getLore();
		}
		//Item has no lore, so return a blank ArrayList<String>
		return new ArrayList<String>();
	}

	/**
	 * Check the players inventory for an item with a specific material and name
	 * Uses a fuzzy search to determine if the item is in their inventory
	 *
	 * @param player   player who's inventory we're checking
	 * @param material The material type were checking for
	 * @param name     The name we're doing a fuzzy search against for
	 * @return true if they have the item, false otherwise
	 */
	public static boolean playerHasItem(Player player, Material material, String name) {
		HashMap<Integer, ? extends ItemStack> playerInventoryItems = player.getInventory().all(material);
		for (Entry<Integer, ? extends ItemStack> inventoryItem : playerInventoryItems.entrySet()) {
			if (itemNameContains(inventoryItem.getValue(), name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get lore of item at specific line
	 *
	 * @param itemStack Item to get lore of
	 * @param line      Index of lore to get
	 * @return String of lore if it exists, otherwise null
	 */
	public static String getItemLore(ItemStack itemStack, int line) {
		if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
			try {
				return itemStack.getItemMeta().getLore().get(line);
			} catch (IndexOutOfBoundsException Exception) {
				Exception.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * Add lines of lore to an item
	 *
	 * @param itemStack item stack to add lore on
	 * @param loreLines the lore lines to add to the item
	 * @return itemstack with the new lore lines added
	 */
	public static ItemStack addLore(ItemStack itemStack, String... loreLines) {
		return addLore(itemStack, Arrays.asList(loreLines));
	}

	/**
	 * Add lines of lore to an item
	 *
	 * @param itemStack item stack to add lore on
	 * @param loreLines the lore lines to add to the item
	 * @return itemstack with the new lore lines added
	 */
	public static ItemStack addLore(ItemStack itemStack, List<String> loreLines) {
		//Get the metadata for our item
		ItemMeta itemMeta = getItemMeta(itemStack);
		//Boolean statements; Woo! Our newItemLore = the current items lore, if it has lore; otherwise a blank arraylist
		List<String> newItemLore = hasLore(itemMeta) ? getLore(itemMeta) : new ArrayList<String>();
		//Add all the lines passed to this method to the items current lore
		newItemLore.addAll(loreLines);
		itemMeta = setLore(itemMeta, newItemLore);
		return setItemMeta(itemStack, itemMeta);
	}

	/**
	 * Check if an item has lore
	 *
	 * @param itemStack itemstack to check
	 * @return true if the itemstack has lore, false otherwise
	 */
	public static boolean hasLore(ItemStack itemStack) {
		return itemHasMetadata(itemStack) && getItemMeta(itemStack).hasLore();
	}

	/**
	 * Check if an item has lore
	 *
	 * @param itemMeta Metadata to check for lore
	 * @return true if the metadata has lore, false otherwise
	 */
	public static boolean hasLore(ItemMeta itemMeta) {
		return itemMeta.hasLore();
	}

	public static List<String> getLore(ItemStack itemStack) {
		if (hasLore(itemStack)) {
			return getItemMeta(itemStack).getLore();
		} else {
			return new ArrayList<String>();
		}
	}

	public static List<String> getLore(ItemMeta itemMeta) {
		return itemMeta.getLore();
	}

	public static ItemStack setLore(ItemStack itemStack, List<String> loreLines) {
		ItemMeta itemMeta = getItemMeta(itemStack);
		itemMeta.setLore(loreLines);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemMeta setLore(ItemMeta itemMeta, List<String> loreLines) {
		itemMeta.setLore(loreLines);
		return itemMeta;
	}

	/**
	 * Checks if an items lore contains specific text
	 *
	 * @param itemStack Item to check
	 * @param text      Text to check the item for
	 * @return true if the item has the text in its lore, otherwise false.
	 */
	public static boolean itemLoreContains(ItemStack itemStack, String text) {
		if (hasLore(itemStack)) {
			List<String> itemLore = getItemLore(itemStack);
			for (String s : itemLore) {
				if (s.toLowerCase().contains(text.toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Set the name of an Item
	 *
	 * @param itemStack Item to set the name of
	 * @param itemName  The name to give the item
	 */
	public static ItemStack setItemName(ItemStack itemStack, String itemName) {
		ItemMeta iMeta = getItemMeta(itemStack);
		iMeta.setDisplayName(itemName);
		return setItemMeta(itemStack, iMeta);
	}

	/**
	 * Get the name of an item
	 *
	 * @param itemStack Item to get the name of
	 * @return Name of the item if it has a name, otherwise its material type in
	 * lowercase
	 */
	public static String getItemName(ItemStack itemStack) {
		if (hasName(itemStack)) {
			return itemStack.getItemMeta().getDisplayName();
		}
		return itemStack.getType().toString().toLowerCase();
	}

	public static boolean hasName(ItemStack itemStack) {
		return (itemHasMetadata(itemStack) && getItemMeta(itemStack).hasDisplayName());
	}

	/**
	 * Check if an items name contains a sequence of text
	 *
	 * @param item itemStack to check the name of
	 * @param text text to check the items name for
	 * @return true if the item name contains the text, false otherwise
	 */
	public static boolean itemNameContains(ItemStack item, String text) {
		return (getItemName(item).toLowerCase().contains(text.toLowerCase()));
	}

	public static ItemStack removeFromStack(ItemStack itemStack, int amount) {
		int itemStackAmount = itemStack.getAmount();
		if (itemStackAmount > amount) {
			itemStack.setAmount(itemStackAmount - amount);
			return itemStack;
		} else {
			return null;
		}
	}

	/**
	 * Set the color to a piece of leather armor
	 *
	 * @param itemStack
	 * @param color
	 * @return true if the color was set, otherwise false
	 */
	public static ItemStack setColor(ItemStack itemStack, Color color) {
		switch (itemStack.getType()) {
			case LEATHER_BOOTS:
			case LEATHER_CHESTPLATE:
			case LEATHER_HELMET:
			case LEATHER_LEGGINGS:
				LeatherArmorMeta itemMeta = (LeatherArmorMeta) getItemMeta(itemStack);
				itemMeta.setColor(color);
				return setItemMeta(itemStack, itemMeta);
			default:
				return itemStack;
		}
	}

	public static boolean addEnchantment(ItemStack itemStack, Enchantment enchantment, int enchantmentLevel, boolean ignoreRestrictions) {
		ItemMeta itemMeta = getItemMeta(itemStack);
		boolean enchantmentStatus = itemMeta.addEnchant(enchantment, enchantmentLevel, ignoreRestrictions);
		setItemMeta(itemStack, itemMeta);
		return enchantmentStatus;
	}

	public static boolean addUnsafeEnchantment(ItemStack itemStack, Enchantment enchantment, int enchantmentLevel) {
		return addEnchantment(itemStack, enchantment, enchantmentLevel, true);
	}

	public static boolean addEnchantment(ItemStack itemStack, Enchantment enchantment, int enchantmentLevel) {
		return addEnchantment(itemStack, enchantment, enchantmentLevel, false);
	}

	@Deprecated
	public static ItemStack setItemLore(ItemStack itemStack, List<String> itemLore) {
		return setLore(itemStack, itemLore);
	}

	/**
	 * Make a new item stack with the given material
	 *
	 * @param material Material to create the itemstack with
	 * @return a new item stack of the given material
	 */
	public static ItemStack makeItemStack(Material material) {
		return new ItemStack(material);
	}

	public static Material getItemTypeMaterial(ItemType itemType) {
		return getMaterialById(itemType.getID());
	}

	public static Material getMaterialById(int id) {
		return Material.getMaterial(id);
	}

	/**
	 * Make an itemstack with a specific material and display name
	 *
	 * @param material material of the itemstack
	 * @param itemName name to give the itemstack
	 * @return Itemstack with the material and name
	 */
	public static ItemStack makeItemStack(Material material, String itemName) {
		ItemStack itemStack = makeItemStack(material);
		return setItemName(itemStack, itemName);
	}

	public static ItemStack makeItemStack(Material material, String itemName, List<String> itemLore) {
		ItemStack itemStack = makeItemStack(material, itemName);
		return setLore(itemStack, itemLore);
	}

	public static ItemStack makeLeatherItemStack(Material material, String itemName, List<String> itemLore, Map<Enchantment, Integer> enchantments, Color itemColor) {
		ItemStack itemStack = makeItemStack(material, itemName, itemLore);
		for (Entry<Enchantment, Integer> itemEnchantment : enchantments.entrySet()) {
			addEnchantment(itemStack, itemEnchantment.getKey(), itemEnchantment.getValue(), true);
		}
		return setColor(itemStack, itemColor);
	}

	public static ItemStack makeLeatherItemStack(Material material, Color leatherColor) {
		ItemStack itemStack = new ItemStack(material);
		return setColor(itemStack, leatherColor);
	}

	public static ItemStack makeLeatherItemStack(Material material, String itemName, List<String> itemLore, Color itemColor) {
		ItemStack itemStack = makeItemStack(material, itemName, itemLore);
		return setColor(itemStack, itemColor);
	}

	public static ItemStack getSkull(String playerName) {
		ItemStack skullStack = new MaterialData(Material.SKULL_ITEM, (byte) 3).toItemStack();
		SkullMeta skullMeta = (SkullMeta) skullStack.getItemMeta();
		skullMeta.setOwner(playerName);
		skullStack.setItemMeta(skullMeta);
		return skullStack;
	}

	public static ItemStack getSkull(String playerName, String itemName) {
		ItemStack skull = getSkull(playerName);
		setItemName(skull, itemName);
		return skull;
	}

	public static String getFormattedMaterialName(ItemStack itemStack) {
		return getFormattedMaterialName(itemStack.getType());
	}

	public static String getFormattedMaterialName(Material Material) {
		return WordUtils.capitalize(Material.name().toLowerCase().replaceAll("_", " "));
	}

	public static Material unFormatMaterialName(String string) {
		return Material.valueOf(string.toUpperCase().replaceAll(" ", "_"));
	}

	public static Dye getDye(DyeColor dyeColor) {
		Dye dye = new Dye();
		dye.setColor(dyeColor);
		return dye;
	}

	public static MaterialData getMaterialDataFromString(String idDatavalue) {
		MaterialData materialData;
		if (idDatavalue.contains(":")) {
			String[] materialSplit = idDatavalue.split(":");
			int materialId = Integer.parseInt(materialSplit[0]);
			int dataValue = Integer.parseInt(materialSplit[1]);
			materialData = new MaterialData(Material.getMaterial(materialId), (byte) dataValue);
		} else {
			materialData = new MaterialData(Material.getMaterial(Integer.parseInt(idDatavalue)));
		}
		return materialData;
	}

	public static MaterialData getMaterialData(int itemID, int dataValue) {
		return new MaterialData(Material.getMaterial(itemID), (byte) dataValue);
	}

	public static MaterialData getMaterialData(Material material, int dataValue) {
		return new MaterialData(material, (byte) dataValue);
	}

	public static ItemStack convertBlockToItem(Block block) {
		return new ItemStack(BlockHandler.getBlockMaterial(block));
	}

	public static List<Recipe> getRecipes(ItemStack itemStack) {
		return Bukkit.getServer().getRecipesFor(itemStack);
	}

	public static void showFurnaceRecipe(Player player, FurnaceRecipe furnaceRecipe) {
		PlayerHandler.sendMessage(player, Messages.FURNACE_RECIPE(furnaceRecipe.getResult(), furnaceRecipe.getInput()));
	}

	public static void showShapelessRecipe(Player player, ShapelessRecipe shapelessRecipe) {
		//Get the ingredients required for this recipe
		List<ItemStack> recipeIngredients = shapelessRecipe.getIngredientList();
		//Create a map for the recipes items
		Map<Integer, ItemStack> recipeItems = new HashMap<>();
		PlayerWrapper playerWrapper = PlayerHandler.getData(player);

		//Put each item in their respective spot
		for (int i = 0; i < recipeIngredients.size(); i++) {
			recipeItems.put(i + 1, recipeIngredients.get(i));
		}

		//Open the workbench inventory
		InventoryView inventoryView = InventoryHandler.openWorkbench(player);
		//Set the items in our inventory
		InventoryHandler.setViewItems(inventoryView, recipeItems);
		playerWrapper.setViewingRecipe(true);
	}

	public static void showShapedRecipe(Player player, ShapedRecipe shapedRecipe) {
		//Get a map of all our ingredients
		Map<Character, ItemStack> itemIngredients = shapedRecipe.getIngredientMap();
		//Get the shape of our recipe
		String[] recipeShape = shapedRecipe.getShape();
		//Create a new list used to create the inventory
		Map<Integer, ItemStack> itemRecipe = new HashMap<>();
		PlayerWrapper playerWrapper = PlayerHandler.getData(player);
		player.closeInventory();
		//Loop through all the items of the shapes (row 1, 2, and 3)
		for (int shapeIterator = 0; shapeIterator < recipeShape.length; shapeIterator++) {
			String recipeRow = recipeShape[shapeIterator];
			char[] rowCharacters = recipeRow.toCharArray();
			//Loop through all the characters in the rowCharachers array and add the
			//Respective itemstack to the retrieved item
			for (int ingredientIterator = 0; ingredientIterator < recipeRow.length(); ingredientIterator++) {
				ItemStack itemStack = itemIngredients.get(rowCharacters[ingredientIterator]);
				itemStack.setAmount(0);
				itemRecipe.put(shapeIterator * 3 + ingredientIterator + 1, itemStack);
			}
		}

		//Open the workbench view
		InventoryView inventoryView = InventoryHandler.openWorkbench(player);
		//Set the item recipe
		InventoryHandler.setViewItems(inventoryView, itemRecipe);
		playerWrapper.setViewingRecipe(true);
	}

	public static boolean showRecipe(Player player, ItemStack itemStack, int recipeNumber) {
		//Check if the items type
		if (itemStack != null && itemStack.getType() != Material.AIR) {
			List<Recipe> recipesForItem = getRecipes(itemStack);
			int recipeAmount = recipesForItem.size();
			if (recipeAmount > 0 && (recipeNumber >= 0 || recipeNumber < recipeAmount)) {
				//Get the recipe requested for the item requested
				Recipe recipe = recipesForItem.get(recipeNumber);
				//Get the wrapped player data
				if (recipe instanceof FurnaceRecipe) {
					//Show the furnace recipe to the player
					showFurnaceRecipe(player, (FurnaceRecipe) recipe);
				} else if (recipe instanceof ShapedRecipe) {
					//Show the shaped recipe to the player
					showShapedRecipe(player, (ShapedRecipe) recipe);
				} else if (recipe instanceof ShapelessRecipe) {
					//Show the shapeless recipe to the player
					showShapelessRecipe(player, (ShapelessRecipe) recipe);
				}
				//Set their wrapper object as viewing a recipe
				return true;
			}
		}
		return false;
	}

	/**
	 * Show the first recipe used to craft the passed itemstack to the player
	 *
	 * @param player    player to show the recipe to
	 * @param itemStack item we're getting the recipe for
	 * @return true if they were shown the recipe, false otherwise
	 */
	public static boolean showRecipe(Player player, ItemStack itemStack) {
		return showRecipe(player, itemStack, 0);
	}

	/**
	 * Sets an items durability to either full-red or full-orange based on arguments
	 *
	 * @param itemStack item stack to change the durability bar on
	 * @param isRed     whether or not we want the bar to be red; if false, the bar will be orange
	 */
	public static void colouredDurability(ItemStack itemStack, boolean isRed) {
		itemStack.setDurability((short) (isRed ? 1000 : itemStack.getType().getMaxDurability() * 2));
	}

	/**
	 * Repair an items durability
	 *
	 * @param itemStack item to repair
	 * @return true if the item was repaired; false if the item is a block, or unable to be repaired
	 */
	public static boolean repairItem(ItemStack itemStack) {
		Material material = itemStack.getType();
		if (itemStack != null && itemStack.getDurability() != 0 && !material.isBlock()) {
			itemStack.setDurability((short) 0);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Repair multiple item stacks
	 *
	 * @param itemStacks items to be repaired
	 * @return integer of how many items were repaired
	 */
	public static int repairItems(ItemStack... itemStacks) {
		int repairedItems = 0;
		for (int i = 0; i < itemStacks.length; i++) {
			ItemStack itemStack = itemStacks[i];
			if (itemStack == null) {
				continue;
			}
			//If the item was repaired successfully, increment the repaired items count
			//Otherwise don't add anything
			repairedItems += repairItem(itemStack) ? 1 : 0;
		}
		return repairedItems;
	}

}