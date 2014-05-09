package com.caved_in.commons.item;

import com.caved_in.commons.Messages;
import com.caved_in.commons.block.Blocks;
import com.caved_in.commons.inventory.Inventories;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.caved_in.commons.utilities.StringUtil;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;

import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

public class Items {

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
	public static boolean hasMetadata(ItemStack itemStack) {
		return itemStack.hasItemMeta();
	}

	/**
	 * Get the metadata attached to an itemstack
	 *
	 * @param itemStack
	 * @return ItemMeta of the item if it exists, otherwise null.
	 */
	public static ItemMeta getMetadata(ItemStack itemStack) {
		return itemStack.getItemMeta();
	}

	/**
	 * Set the MetaData on an Item Stack
	 *
	 * @param itemStack item stack to set the metadata on
	 * @param itemMeta  The metadata to set on our item
	 * @return The itemstack passed, but with its metadata changed.
	 */
	public static ItemStack setMetadata(ItemStack itemStack, ItemMeta itemMeta) {
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	/**
	 * Get lore of item at specific line
	 *
	 * @param itemStack Item to get lore of
	 * @param line      Index of lore to get
	 * @return String of lore if it exists, otherwise null
	 */
	public static String getLore(ItemStack itemStack, int line) {
		if (!hasLore(itemStack)) {
			return null;
		}

		try {
			return getLore(itemStack).get(line);
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
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
		ItemMeta itemMeta = getMetadata(itemStack);
		//Boolean statements; Woo! Our newItemLore = the current items lore, if it has lore; otherwise a blank arraylist
		List<String> newItemLore = hasLore(itemMeta) ? getLore(itemMeta) : new ArrayList<String>();
		//Add all the lines passed to this method to the items current lore
		newItemLore.addAll(loreLines);
		itemMeta = setLore(itemMeta, newItemLore);
		return setMetadata(itemStack, itemMeta);
	}

	/**
	 * Check if an item has lore
	 *
	 * @param itemStack itemstack to check
	 * @return true if the itemstack has lore, false otherwise
	 */
	public static boolean hasLore(ItemStack itemStack) {
		return hasMetadata(itemStack) && getMetadata(itemStack).hasLore();
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
		if (!hasLore(itemStack)) {
			return null;
		}
		return getMetadata(itemStack).getLore();
	}

	public static List<String> getLore(ItemMeta itemMeta) {
		return itemMeta.getLore();
	}

	public static ItemStack setLore(ItemStack itemStack, List<String> loreLines) {
		ItemMeta itemMeta = getMetadata(itemStack);
		List<String> lore = new ArrayList<>();
		for (String line : loreLines) {
			if (line != null) {
				lore.add(StringUtil.formatColorCodes(line));
			}
		}
		itemMeta.setLore(lore);
		setMetadata(itemStack, itemMeta);
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
	public static boolean loreContains(ItemStack itemStack, String text) {
		if (!hasLore(itemStack)) {
			return false;
		}

		List<String> itemLore = getLore(itemStack);
		for (String s : itemLore) {
			if (s.toLowerCase().contains(text.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Set the name of an Item
	 *
	 * @param item Item to set the name of
	 * @param name The name to give the item
	 */
	public static ItemStack setName(ItemStack item, String name) {
		ItemMeta meta = getMetadata(item);
		meta = setName(meta, name);
		return setMetadata(item, meta);
	}

	public static ItemMeta setName(ItemMeta meta, String name) {
		meta.setDisplayName(StringUtil.formatColorCodes(name));
		return meta;
	}

	/**
	 * Get the name of an item
	 *
	 * @param itemStack Item to get the name of
	 * @return Name of the item if it has a name, otherwise its material type in
	 * lowercase
	 */
	public static String getName(ItemStack itemStack) {
		if (!hasName(itemStack)) {
			return itemStack.getType().toString().toLowerCase();
		}
		return getMetadata(itemStack).getDisplayName();
	}

	public static boolean hasName(ItemStack itemStack) {
		return (hasMetadata(itemStack) && getMetadata(itemStack).hasDisplayName());
	}

	public static boolean hasMaterialData(ItemStack item, int id) {
		return item.getData().getData() == id;
	}

	/**
	 * Check if an items name contains a sequence of text
	 *
	 * @param item itemStack to check the name of
	 * @param text text to check the items name for
	 * @return true if the item name contains the text, false otherwise
	 */
	public static boolean nameContains(ItemStack item, String text) {
		return (getName(item).toLowerCase().contains(text.toLowerCase()));
	}

	public static ItemStack removeFromStack(ItemStack itemStack, int amount) {
		int itemStackAmount = itemStack.getAmount();

		if (itemStackAmount <= amount) {
			return null;
		}

		itemStack.setAmount(itemStackAmount - amount);
		return itemStack;
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
				LeatherArmorMeta itemMeta = (LeatherArmorMeta) getMetadata(itemStack);
				itemMeta.setColor(color);
				return setMetadata(itemStack, itemMeta);
			default:
				return itemStack;
		}
	}


	public static boolean addEnchantment(ItemStack itemStack, Enchantment enchantment, int enchantmentLevel, boolean ignoreRestrictions) {
		ItemMeta meta = getMetadata(itemStack);
		boolean enchanted = meta.addEnchant(enchantment, enchantmentLevel, ignoreRestrictions);
		setMetadata(itemStack, meta);
		return enchanted;
	}

	public static boolean addUnsafeEnchantment(ItemStack itemStack, Enchantment enchantment, int enchantmentLevel) {
		return addEnchantment(itemStack, enchantment, enchantmentLevel, true);
	}

	public static boolean addEnchantment(ItemStack itemStack, Enchantment enchantment, int enchantmentLevel) {
		return addEnchantment(itemStack, enchantment, enchantmentLevel, false);
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
	public static ItemStack makeItem(Material material, String itemName) {
		ItemStack itemStack = makeItemStack(material);
		return setName(itemStack, itemName);
	}

	public static ItemStack makeItem(MaterialData materialData, String name, String... lore) {
		ItemStack itemStack = materialData.toItemStack();
		itemStack = setName(itemStack, name);
		return setLore(itemStack, Arrays.asList(lore));
	}

	public static ItemStack makeItem(Material material, String itemName, List<String> itemLore) {
		ItemStack itemStack = makeItem(material, itemName);
		return setLore(itemStack, itemLore);
	}

	public static ItemStack makeLeatherItem(Material material, String itemName, List<String> itemLore, Map<Enchantment, Integer> enchantments, Color itemColor) {
		ItemStack itemStack = makeItem(material, itemName, itemLore);
		for (Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
			addEnchantment(itemStack, enchantment.getKey(), enchantment.getValue(), true);
		}
		return setColor(itemStack, itemColor);
	}

	public static ItemStack makeLeatherItem(Material material, Color leatherColor) {
		ItemStack itemStack = new ItemStack(material);
		return setColor(itemStack, leatherColor);
	}

	public static ItemStack makeLeatherItem(Material material, String itemName, List<String> itemLore, Color itemColor) {
		ItemStack itemStack = makeItem(material, itemName, itemLore);
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
		setName(skull, itemName);
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
		return new ItemStack(Blocks.getBlockMaterial(block));
	}

	public static List<Recipe> getRecipes(ItemStack itemStack) {
		return Bukkit.getServer().getRecipesFor(itemStack);
	}

	public static void showFurnaceRecipe(Player player, FurnaceRecipe furnaceRecipe) {
		Players.sendMessage(player, Messages.recipeFurnace(furnaceRecipe.getResult(), furnaceRecipe.getInput()));
	}

	public static void showShapelessRecipe(Player player, ShapelessRecipe shapelessRecipe) {
		//Get the ingredients required for this recipe
		List<ItemStack> recipeIngredients = shapelessRecipe.getIngredientList();
		//Create a map for the recipes items
		Map<Integer, ItemStack> recipeItems = new HashMap<>();
		PlayerWrapper playerWrapper = Players.getData(player);

		//Put each item in their respective spot
		for (int i = 0; i < recipeIngredients.size(); i++) {
			recipeItems.put(i + 1, recipeIngredients.get(i));
		}

		//Open the workbench inventory
		InventoryView inventoryView = Inventories.openWorkbench(player);
		//Set the items in our inventory
		Inventories.setViewItems(inventoryView, recipeItems);
		playerWrapper.setViewingRecipe(true);
	}

	public static void showShapedRecipe(Player player, ShapedRecipe shapedRecipe) {
		//Get a map of all our ingredients
		Map<Character, ItemStack> itemIngredients = shapedRecipe.getIngredientMap();
		//Get the shape of our recipe
		String[] recipeShape = shapedRecipe.getShape();
		//Create a new list used to create the inventory
		Map<Integer, ItemStack> itemRecipe = new HashMap<>();
		PlayerWrapper playerWrapper = Players.getData(player);
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
		InventoryView inventoryView = Inventories.openWorkbench(player);
		//Set the item recipe
		Inventories.setViewItems(inventoryView, itemRecipe);
		playerWrapper.setViewingRecipe(true);
	}

	public static boolean showRecipe(Player player, ItemStack itemStack, int recipeNumber) {
		if (itemStack == null || itemStack.getType() == Material.AIR) {
			return false;
		}

		List<Recipe> recipes = getRecipes(itemStack);
		int recipeCount = recipes.size();
		if (recipeCount == 0) {
			return false;
		}

		if (recipeNumber > 0 && (recipeNumber < recipeCount)) {
			//Get the recipe requested for the item requested
			Recipe recipe = recipes.get(recipeNumber);
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
		if (itemStack == null || itemStack.getDurability() == 0 || itemStack.getType().isBlock()) {
			return false;
		}
		itemStack.setDurability((short) 0);
		return true;
	}

	/**
	 * Repair multiple item stacks
	 *
	 * @param itemStacks items to be repaired
	 * @return integer of how many items were repaired
	 */
	public static int repairItems(ItemStack... itemStacks) {
		int repairedItems = 0;
		for (ItemStack itemStack : itemStacks) {
			if (itemStack == null || itemStack.getType() == Material.AIR) {
				continue;
			}
			//If the item was repaired successfully, increment the repaired items count
			//Otherwise don't add anything
			repairedItems += repairItem(itemStack) ? 1 : 0;
		}
		return repairedItems;
	}


	public static boolean isBook(ItemStack itemStack) {
		return (getMetadata(itemStack) instanceof BookMeta);
	}

	public static ItemStack makeBook(String title, String author, String... pages) {
		return makeBook(title, author, Arrays.asList(pages));
	}

	public static ItemStack makeBook(String title, String author, List<String> pages) {
		ItemStack book = makeItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta = (BookMeta) getMetadata(book);
		bookMeta.setTitle(title);
		bookMeta.setAuthor(author);
		bookMeta.setPages(pages);
		return setMetadata(book, bookMeta);
	}

}