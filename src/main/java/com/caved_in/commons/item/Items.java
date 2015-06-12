package com.caved_in.commons.item;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.block.Blocks;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.exceptions.InvalidMaterialNameException;
import com.caved_in.commons.inventory.Inventories;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.caved_in.commons.utilities.ListUtils;
import com.caved_in.commons.utilities.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
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
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Items {

    /**
     * A full set of diamond armor. Includes Boots, Leggings, ChestPlate, and Helmet.
     */
    public static final ItemStack[] DIAMOND_ARMOR = new ItemStack[]{
            makeItem(Material.DIAMOND_BOOTS),
            makeItem(Material.DIAMOND_LEGGINGS),
            makeItem(Material.DIAMOND_CHESTPLATE),
            makeItem(Material.DIAMOND_HELMET),
    };

    /**
     * A full set of chain armor. Includes Boots, Leggings, ChestPlate, and Helmet.
     */
    public static final ItemStack[] CHAIN_ARMOR = new ItemStack[]{
            makeItem(Material.CHAINMAIL_BOOTS),
            makeItem(Material.CHAINMAIL_LEGGINGS),
            makeItem(Material.CHAINMAIL_CHESTPLATE),
            makeItem(Material.CHAINMAIL_HELMET)
    };

    /**
     * A full set of gold armor. Includes Boots, Leggings, ChestPlate, and Helmet.
     */
    public static final ItemStack[] GOLD_ARMOR = new ItemStack[]{
            makeItem(Material.GOLD_BOOTS),
            makeItem(Material.GOLD_LEGGINGS),
            makeItem(Material.GOLD_CHESTPLATE),
            makeItem(Material.GOLD_HELMET)
    };

    /**
     * A full set of iron armor. Includes Boots, Leggings, ChestPlate, and Helmet.
     */
    public static final ItemStack[] IRON_ARMOR = new ItemStack[]{
            makeItem(Material.IRON_BOOTS),
            makeItem(Material.IRON_LEGGINGS),
            makeItem(Material.IRON_CHESTPLATE),
            makeItem(Material.IRON_HELMET)
    };

    /**
     * A full set of leather armor. Includes Boots, Leggings, Chestplate, and Helmet.
     */
    public static final ItemStack[] LEATHER_ARMOR = new ItemStack[]{
            makeItem(Material.LEATHER_BOOTS),
            makeItem(Material.LEATHER_LEGGINGS),
            makeItem(Material.LEATHER_CHESTPLATE),
            makeItem(Material.LEATHER_HELMET)
    };

    private static Set<Material> armorMaterials = Sets.newHashSet(
            Material.LEATHER_BOOTS,
            Material.LEATHER_CHESTPLATE,
            Material.LEATHER_HELMET,
            Material.LEATHER_LEGGINGS,
            Material.GOLD_BOOTS,
            Material.GOLD_CHESTPLATE,
            Material.GOLD_HELMET,
            Material.GOLD_LEGGINGS,
            Material.IRON_BOOTS,
            Material.IRON_CHESTPLATE,
            Material.IRON_HELMET,
            Material.IRON_LEGGINGS,
            Material.DIAMOND_BOOTS,
            Material.DIAMOND_CHESTPLATE,
            Material.DIAMOND_HELMET,
            Material.DIAMOND_LEGGINGS
    );

    private static final Method TO_NMS = ReflectionUtilities.getMethod(ReflectionUtilities.getCBClass("inventory.CraftItemStack"), "asNMSCopy", ItemStack.class);

    static {
        armorMaterials = Sets.newHashSet();
        Collections.addAll(armorMaterials);
    }


    /**
     * Retrive the NMS object used to manage the given itemstack
     *
     * @param stack item to get the nms object for
     * @return NMS object used to manage the given itemstack
     */
    public static Object toNMS(ItemStack stack) {
        return ReflectionUtilities.invokeMethod(TO_NMS, null, stack);
    }

    /**
     * Check whether or not an item has enchantments.
     *
     * @param itemStack itemstack to check for enchantments on.
     * @return true if the item has any enchantments, false otherwise.
     */
    public static boolean hasEnchantments(ItemStack itemStack) {
        return hasMetadata(itemStack) && getMetadata(itemStack).hasEnchants();
    }

    /**
     * Check whether or not an item has metadata
     *
     * @param itemStack itemstack to check for metadata on.
     * @return true if the item has metadata, false otherwise
     */
    public static boolean hasMetadata(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }

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
     * Clear existing lore on an item.
     *
     * @param item item to remove the lore from.
     */
    public static void clearLore(ItemStack item) {
        setLore(item, Arrays.asList());
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
        List<String> newItemLore = hasLore(itemMeta) ? getLore(itemMeta) : new ArrayList<>();
        //Add all the lines passed to this method to the items current lore
        for (String line : loreLines) {
            if (line == null) {
                continue;
            }

            newItemLore.add(StringUtil.formatColorCodes(line));
        }
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

    /**
     * Retrieve the lore of an item if available.
     *
     * @param itemStack itemstack to get the lore of.
     * @return null if the item has no lore, otherwise a(n) list of lore-lines.
     */
    public static List<String> getLore(ItemStack itemStack) {
        if (!hasLore(itemStack)) {
            return null;
        }
        return getMetadata(itemStack).getLore();
    }

    /**
     * Retrieve the lore off of an ItemMeta object.
     *
     * @param itemMeta meta to get the lore of.
     * @return lore of the ItemMeta object,
     */
    public static List<String> getLore(ItemMeta itemMeta) {
        return itemMeta.getLore();
    }

    /**
     * Check if the item has lore available at a specific line. 0-based index.
     *
     * @param item item to get the lore of.
     * @param line line to get the lore at.
     * @return true if the item has a non-empty line of at the given index, false otherwise.
     */
    public static boolean hasLoreAtLine(ItemStack item, int line) {
        List<String> loreLines = getLore(item);

        if (loreLines.size() > line) {
            return StringUtils.isNotEmpty(loreLines.get(line));
        }

        return false;
    }

    /**
     * Set the lore at a specific line for the given item. If there's currently no lore at the line, the operation will fail.
     *
     * @param item item to assign the lore to
     * @param line line to set the lore at
     * @param lore lore to assign at the given slot. (Note: Formats color codes)
     */
    public static void setLore(ItemStack item, int line, String lore) {
        if (!hasLoreAtLine(item, line)) {
            return;
        }

        List<String> loreLines = getLore(item);
        //todo implement option to fill with blanks.
        loreLines.set(line, StringUtil.formatColorCodes(lore));
        setLore(item, loreLines);
    }

    /**
     * Set the lore on an item.
     *
     * @param itemStack item to set the lore on
     * @param loreLines lore to assign to the item. (Note: Formats color codes)
     * @return the item modified to have the lore assigned.
     */
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

    /**
     * Change the lore assigned to an instance of ItemMeta.
     *
     * @param itemMeta  metadata to assign the lore to.
     * @param loreLines lines of lore to assign to the item. (Note: Formats color codes)
     * @return itemmeta with the formatted lore applied.
     */
    public static ItemMeta setLore(ItemMeta itemMeta, List<String> loreLines) {
        itemMeta.setLore(loreLines.stream().map(StringUtil::formatColorCodes).collect(Collectors.toList()));
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
        int i = 0;
        for (String s : itemLore) {
            i++;
            //If the line of lore is blank then skip it
            if (s == null || s.isEmpty()) {
                continue;
            }
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

    /**
     * Change the display name on an instance of ItemMeta.
     *
     * @param meta meta to change the display name on.
     * @param name displayname to assign to the meta. (Note: Formats color codes)
     * @return modified itemmeta with the display name applied.
     */
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
        if (itemStack == null) {
            throw new NullPointerException("Unable to get the name of a null item");
        }

        if (!hasName(itemStack)) {
            return getFormattedMaterialName(itemStack);
        }
        return StringUtil.stripColor(getMetadata(itemStack).getDisplayName());
    }

    /**
     * Check whether or not the item has a display name.
     *
     * @param itemStack item to check
     * @return true if the item has a display name, false otherwise.
     */
    public static boolean hasName(ItemStack itemStack) {
        return (hasMetadata(itemStack) && getMetadata(itemStack).hasDisplayName());
    }

    /**
     * Check if the item has the given material-data value assigned to it.
     *
     * @param item item to check.
     * @param id   material-data value to search for.
     * @return true if the item has the given data-value assigned, false otherwise.
     */
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
        return StringUtils.containsIgnoreCase(getName(item), text);
    }

    /**
     * Remove an amount of items from the given item stack.
     *
     * @param itemStack stack of items to modify
     * @param amount    amount of items to remove from the stack
     * @return the modified itemstack, amount deducted.
     */
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
     * @param itemStack itemstack
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


    /**
     * Add an enchantment to the given item
     *
     * @param itemStack          itemstack to apply the enchantment to
     * @param enchantment        enchantment to add to the item.
     * @param enchantmentLevel   level to give the enchantment
     * @param ignoreRestrictions whether or not to ignore level and item restrictions on the item (unsafe enchanting)
     * @return true if the enchantment was added to the item, false otherwise.
     */
    public static boolean addEnchantment(ItemStack itemStack, Enchantment enchantment, int enchantmentLevel, boolean ignoreRestrictions) {
        ItemMeta meta = getMetadata(itemStack);
        boolean enchanted = meta.addEnchant(enchantment, enchantmentLevel, ignoreRestrictions);
        setMetadata(itemStack, meta);
        return enchanted;
    }

    /**
     * Add an enchantment to the given item.
     *
     * @param itemStack        itemstack to enchant
     * @param enchantment      enchantment to add to the item
     * @param enchantmentLevel level to give to the enchantment.
     * @return true if the enchantment was added to the item, false otherwise.
     */
    public static boolean addUnsafeEnchantment(ItemStack itemStack, Enchantment enchantment, int enchantmentLevel) {
        itemStack.addUnsafeEnchantment(enchantment, enchantmentLevel);
        boolean enchanted = Items.hasEnchantment(itemStack, enchantment);
        return enchanted;
    }

    /**
     * Add an enchantment to the item, following all enchantment restrictions.
     *
     * @param itemStack        item to enchant.
     * @param enchantment      enchatment to add to the item.
     * @param enchantmentLevel level to give the enchantment.
     * @return true if the enchantment as added, false otherwise.
     */
    public static boolean addEnchantment(ItemStack itemStack, Enchantment enchantment, int enchantmentLevel) {
        return addEnchantment(itemStack, enchantment, enchantmentLevel, false);
    }

    /**
     * Add a varying amount of {@link com.caved_in.commons.item.EnchantWrapper} to the item.
     *
     * @param target   item to enchant.
     * @param enchants wrappers to apply to the item.
     */
    public static void addEnchantment(ItemStack target, EnchantWrapper... enchants) {
        for (EnchantWrapper wrapper : enchants) {
            addEnchantment(target, wrapper.getEnchantment(), wrapper.getLevel());
        }

    }

    /**
     * Add a collection of {@link org.bukkit.enchantments.EnchantmentWrapper} to the item target.
     *
     * @param target   item to enchant
     * @param wrappers Wrappers to enchant the item with.
     */
    public static void addEnchantments(ItemStack target, Collection<EnchantWrapper> wrappers) {
        for (EnchantWrapper wrapper : wrappers) {
            addEnchantment(target, wrapper.getEnchantment(), wrapper.getLevel());
        }
    }

    /**
     * Remove all the enchantments from an item.
     * @param item item to clear of enchantments.
     */
    public static void clearEnchantments(ItemStack item) {
        getMetadata(item).getEnchants().clear();
    }

    /**
     * Assign a collection of enchantments, and levels, to the given item.
     *
     * @param target   item to enchant.
     * @param enchants collection of {@link org.bukkit.enchantments.EnchantmentWrapper} to add to the item.
     */
    public static void setEnchantments(ItemStack target, Set<EnchantWrapper> enchants) {
        ItemMeta meta = getMetadata(target);

        //Clear the current enchantments.
        meta.getEnchants().clear();

        for (EnchantWrapper wrapper : enchants) {
            addEnchantment(target, wrapper.getEnchantment(), wrapper.getLevel());
        }
    }

    /**
     * Replace a substring inside the given items name!
     * @param target item to rename.
     * @param search string to search for, that will be replaced.
     * @param replace replacement for the searched string.
     * @return the ItemStack that's been renamed.
     */
    public static ItemStack replaceInName(ItemStack target, String search, String replace) {
        if (!hasName(target)) {
            return target;
        }

        String name = getName(target);

        if (StringUtils.containsIgnoreCase(search, replace)) {
            name = StringUtils.replace(name, search, replace);
        }

        return Items.setName(target, name);
    }

    /**
     * Compare the enchantments of 2 items and see if they match.
     * @param item item to match the enchantments against.
     * @param compareItem comparing item, to see if the enchantments match against the first.
     * @return true if both items have the same enchantments, false otherwise.
     */
    public static boolean hasSameEnchantments(ItemStack item, ItemStack compareItem) {
        if (!Items.hasEnchantments(item) || !Items.hasEnchantments(compareItem)) {
            return false;
        }

        Map<Enchantment, Integer> enchantments = item.getEnchantments();
        Map<Enchantment, Integer> checkEnchants = compareItem.getEnchantments();
        if (enchantments.size() != checkEnchants.size()) {
            return false;
        }
        for (Map.Entry<Enchantment, Integer> enchantmentEntry : checkEnchants.entrySet()) {
            Enchantment enchantment = enchantmentEntry.getKey();
            if (enchantments.containsKey(enchantment) && enchantments.get(enchantment).equals(enchantmentEntry.getValue())) {
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * Check if the given item has the queried enchantment. Doesn't match against levels for the enchantment.
     * @param item item to check enchantments for.
     * @param enchant enchantment to check for on the item.
     * @return true if the item has the enchantment in question, false otherwise.
     */
    public static boolean hasEnchantment(ItemStack item, Enchantment enchant) {
        if (!Items.hasEnchantments(item)) {
            return false;
        }


        Set<EnchantWrapper> enchants = getEnchantments(item);
        for (EnchantWrapper wrapper : enchants) {
            if (wrapper.getEnchantment().equals(enchant)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if the given item has the enchantment of the desired level on it.
     * @param item item to search for enchantments on.
     * @param enchant enchantment to look for on the item.
     * @param level level of the enchantment to match against.
     * @return true if the item has the enchantment of the given level, false otherwise.
     */
    public static boolean hasEnchantment(ItemStack item, Enchantment enchant, int level) {
        if (!Items.hasEnchantments(item)) {
            return false;
        }

        Set<EnchantWrapper> enchants = getEnchantments(item);
        for (EnchantWrapper wrapper : enchants) {
            if (wrapper.getEnchantment().equals(enchant) && wrapper.getLevel() == level) {
                return true;
            }
        }
        return false;
    }

    /**
     * Filter a collection of items where the enchantments match that of the item param.
     *
     * @param item         the item we wish to match enchantments against
     * @param itemsToCheck items to search for matching enchantments on
     * @return an arraylist of the items that have enchantments similar to the item param.
     */
    public static List<ItemStack> findSimilarEnchantedItems(ItemStack item, Collection<ItemStack> itemsToCheck) {
        List<ItemStack> items = new ArrayList<>();
        for (ItemStack itemStack : itemsToCheck) {
            if (hasSameEnchantments(item, itemStack)) {
                items.add(itemStack);
            }
        }

        return items;
    }

    /**
     * Retrieve the enchantments that an item has in a set of EnchantmentWrappers.
     * If the item has no enchantments, and empty hashset is returned.
     *
     * @param item item to get the enchantments from.
     * @return a HashSet of the {@link com.caved_in.commons.item.EnchantWrapper} that the item has; If no enchants are available, then an empty hash set is returned.
     */
    public static Set<EnchantWrapper> getEnchantments(ItemStack item) {
        Set<EnchantWrapper> enchants = Sets.newHashSet();

        if (!hasEnchantments(item)) {
            return enchants;
        }

        for (Entry<Enchantment, Integer> enchant : item.getEnchantments().entrySet()) {
            enchants.add(new EnchantWrapper(enchant.getKey(), enchant.getValue(), true));
        }

        return enchants;
    }

    /**
     * Add ItemFlag's to the given item.
     * @param item item to add flags to.
     * @param flag flags to add to the item.
     */
    public static void addFlags(ItemStack item, ItemFlag... flag) {
        ItemMeta meta = getMetadata(item);
        meta.addItemFlags(flag);
        setMetadata(item, meta);
    }

    /**
     * Add a collection of ItemFlags to the given ItemStack
     * @param item item to have flags added on.
     * @param flags collection of ItemFlags to add to the Item.
     */
    public static void addFlags(ItemStack item, Collection<ItemFlag> flags) {
        ItemMeta meta = getMetadata(item);
        flags.forEach(flag -> meta.addItemFlags(flag));
        setMetadata(item, meta);
    }

    /**
     * Make a new item stack with the given material
     *
     * @param material Material to create the itemstack with
     * @return a new item stack of the given material
     */
    public static ItemStack makeItem(Material material) {
        return new ItemStack(material);
    }

    /**
     * Check if the ItemStack is of the given material type.
     * @param itemStack item to check the type of.
     * @param material material to compare the item to.
     * @return true if the item is of the given type, false otherwise.
     */
    public static boolean isType(ItemStack itemStack, Material material) {
        return itemStack != null && material == itemStack.getType();
    }

    /**
     * Check if the ItemStack is a piece of armor.
     * @param itemStack item to check.
     * @return true if the item is a piece of armor, false otherwise.
     */
    public static boolean isArmor(ItemStack itemStack) {
        return isArmor(itemStack.getType());
    }

    /**
     * Check if the given Material is a piece of armor.
     * @param material material to check.
     * @return true if the material is a piece of armor, false otherwise.
     */
    public static boolean isArmor(Material material) {
        return armorMaterials.contains(material);
    }

    /**
     * Check if the item of the given type is a weapon or not.
     * @param itemStack item to check.
     * @return true if the item is a weapon, false otherwise.
     */
    public static boolean isWeapon(ItemStack itemStack) {
        return WeaponType.isItemWeapon(itemStack);
    }

    /**
     * Check if the material is the type of a weapon or not.
     * @param material material to check.
     * @return true if the material is a weapon, false otherwise.
     */
    public static boolean isWeapon(Material material) {
        return WeaponType.isMaterialWeapon(material);
    }

    /**
     * Check if the Item is a tool; Hoe, Axe, Shovel, Flint & Tinder, etc!
     * @param item item to check.
     * @return true if the item is that of any of the tool types, false otherwise.
     */
    public static boolean isTool(ItemStack item) {
        return isTool(item.getType());
    }

    /**
     * Check if the item is of the given tool type.
     * @param item item to check.
     * @param type type to compare the item to.
     * @return true if the item is the of any of the materials matched by the given {@link ToolType}
     */
    public static boolean isTool(ItemStack item, ToolType type) {
        return isTool(item.getType(), type);
    }

    /**
     * Check if the material is of the given tool type.
     * @param material material to check.
     * @param type tooltype to compare the material to.
     * @return true if the material is of the given type, false otherwise.
     */
    public static boolean isTool(Material material, ToolType type) {
        return type.isType(material);
    }

    /**
     * Check if the material is a tool.
     * @param type material to check.
     * @return true if the material is a tool, false otherwise.
     */
    public static boolean isTool(Material type) {
        return ToolType.isTool(type);
    }

    /**
     * Get all the items in a tool set, to their max stack size.
     *
     * @param type set of tools to get a copy of.
     * @return a {@link java.util.HashSet} of {@link org.bukkit.inventory.ItemStack} that the materials in the tool set make.
     */
    public static Set<ItemStack> getToolSet(ToolType type) {
        Set<ItemStack> items = type.getMaterialTypes().stream()
                .map(itemType -> ItemBuilder.of(itemType).amount(itemType.getMaxStackSize()).item())
                .collect(Collectors.toSet());
        return items;
    }

    /**
     * Create a collection of the Tool Set, with all items assigned the given stack size.
     * @param type type of ToolSet to create.
     * @param stackSize size to set each of the ItemStacks to.
     * @return a hashset with each item in the Tools Type
     */
    public static Set<ItemStack> getToolSet(ToolType type, int stackSize) {
        /*
		Create a set of item stacks by mapping the values of each individual material
		type to an item of the desired type, collecting it into a set!
		 */
        Set<ItemStack> items = type.getMaterialTypes().stream()
                .map(itemType -> ItemBuilder.of(itemType).amount(stackSize).item())
                .collect(Collectors.toSet());
        return items;
    }

    /**
     * Retrieve a material by it's ID.
     * @param id id of the material to get.
     * @return material assigned the given ID.
     */
    public static Material getMaterialById(int id) {
        return Material.getMaterial(id);
    }

    /**
     * Create an item with the given material, and data value.
     *
     * @param material material of the item
     * @param dataVal  data value to assign the item.
     * @return itemstack of the given material and data value.
     */
    public static ItemStack makeItem(Material material, int dataVal) {
        return getMaterialData(material, dataVal).toItemStack(1);
    }

    /**
     * Create a leather item, customizing it's name, lore, enchantments and color.
     *
     * @param material     material of the item to create
     * @param itemName     name to assign to the item
     * @param itemLore     lore to assign to the item
     * @param enchantments enchantments to give the item
     * @param itemColor    color to dye the leather
     * @return itemstack with the given material, custon name, custom lore, enchantments, and color.
     */
    public static ItemStack makeLeatherItem(Material material, String itemName, List<String> itemLore, Map<Enchantment, Integer> enchantments, Color itemColor) {
        ItemStack itemStack = ItemBuilder.of(material).name(itemName).lore(itemLore).item();
        for (Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
            addEnchantment(itemStack, enchantment.getKey(), enchantment.getValue(), true);
        }
        return setColor(itemStack, itemColor);
    }

    /**
     * Create a leather item of the given material, and color.
     *
     * @param material     material to make the item of
     * @param leatherColor color to assign to the leather
     * @return the leather item, dyed to the color passed.
     */
    public static ItemStack makeLeatherItem(Material material, Color leatherColor) {
        ItemStack itemStack = new ItemStack(material);
        return setColor(itemStack, leatherColor);
    }

    /**
     * Check whether or not the item is a player skull.
     * Compares material types, data value, and checks if the skull has an owner / skin.
     *
     * @param item item to check if it's a player skull.
     * @return true if the skull is a human skull with an owner, false otherwise.
     */
    public static boolean isPlayerSkull(ItemStack item) {
        if (item.getType() != Material.SKULL_ITEM) {
            return false;
        }

        if (getDataValue(item) != 3) {
            return false;
        }

        try {
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            return meta.hasOwner();
        } catch (Exception e) {
            Commons.getInstance().getLogger().log(Level.SEVERE, "Metadata cast exception", e);
        }

        return true;
    }

    /**
     * Create a skull skinned with the given players name.
     *
     * @param playerName name of the player to get the skull of
     * @return skull with the given player for the skin.
     */
    public static ItemStack getSkull(String playerName) {
        ItemStack skullStack = new MaterialData(Material.SKULL_ITEM, (byte) 3).toItemStack();
        SkullMeta skullMeta = (SkullMeta) skullStack.getItemMeta();
        skullMeta.setOwner(playerName);
        skullStack.setItemMeta(skullMeta);
        return skullStack;
    }

    /**
     * Retrieve the items type in a user-friendly formatted string.
     *
     * @param itemStack item to get the user-friendly name for.
     * @return the name of the item type in a friendly, formatted string.
     */
    public static String getFormattedMaterialName(ItemStack itemStack) {
        return getFormattedMaterialName(itemStack.getType());
    }

    /**
     * Retrieve the material in a user-friendly formatted string.
     *
     * @param Material material to get the user friendly name for
     * @return the material name in a user friendly formatted string.
     */
    public static String getFormattedMaterialName(Material Material) {
        //todo: Pull the material name from a dictionary / enum of better names.
        return WordUtils.capitalize(Material.name().toLowerCase().replaceAll("_", " "));
    }

    /**
     * Retrieve a material object from the formatted string passed. Case insensitive, follows name scheme of Material.name()
     *
     * @param string string to retrieve the material from.
     * @return material parsed from the given string.
     */
    public static Material unFormatMaterialName(String string) {
        return Material.valueOf(string.toUpperCase().replaceAll(" ", "_"));
    }

    /**
     * Retrieve a material by its name, or commonly known alias
     *
     * @param search name of the material to search for
     * @return material with a name or alias matching the search
     * @throws com.caved_in.commons.exceptions.InvalidMaterialNameException if no material has a name or alias matching the search
     */
    public static Material getMaterialByName(String search) throws InvalidMaterialNameException {
        ItemType itemType = ItemType.lookup(search);
        if (itemType == null) {
            throw new InvalidMaterialNameException(search + " is not a valid material name.");
        }
        return getMaterialById(itemType.getID());
    }

    /**
     * Create a new dye item based on the color passed.
     *
     * @param dyeColor color of the dye
     * @return dye item with the associated color.
     */
    public static Dye getDye(DyeColor dyeColor) {
        Dye dye = new Dye();
        dye.setColor(dyeColor);
        return dye;
    }

    /**
     * Retrieve a material-data object based on the item:data / itemname:data string passed
     *
     * @param idDatavalue string to parse to retrieve a material-data object from.
     * @return a material-data object based on the item:data / itemname:data string passed
     * @throws InvalidMaterialNameException
     */
    public static MaterialData getMaterialDataFromString(String idDatavalue) throws InvalidMaterialNameException {
        //If it's just a number, then parse it and return!S
        if (StringUtils.isNumeric(idDatavalue)) {
            return new MaterialData(Material.getMaterial(Integer.parseInt(idDatavalue)));
        }

        String[] materialSplit = null;
        //If there's a data-value attached, parse it
        if (idDatavalue.contains(":")) {
            materialSplit = idDatavalue.split(":");
            //Assure both the numbers are numbers, otherwise return null material data
            boolean firstNumeric = StringUtils.isNumeric(materialSplit[0]);
            boolean secondNumeric = StringUtils.isNumeric(materialSplit[1]);

            //If both elements are numeric, then parse them!
            if (firstNumeric && secondNumeric) {
                int materialId = Integer.parseInt(materialSplit[0]);
                int dataValue = Integer.parseInt(materialSplit[1]);
                return new MaterialData(Material.getMaterial(materialId), (byte) dataValue);
            }
        }

        ItemType itemType;

        //If no data-value was given, and it's a word, then look er' up and see
        //if its material data
        if (materialSplit == null) {
            itemType = ItemType.lookup(idDatavalue);
            if (itemType == null) {
                throw new InvalidMaterialNameException(StringUtil.stripColor(Messages.invalidItem(idDatavalue)));
            }
            return new MaterialData(itemType.getID());
        }

        itemType = ItemType.lookup(materialSplit[0]);
        String dataName = materialSplit[1];
        if (itemType == null) {
            throw new InvalidMaterialNameException(Messages.invalidItem(idDatavalue));
        }
        int itemId = itemType.getID();

        //If they're combining names and numbers, then parse it accordingly
        if (StringUtils.isNumeric(dataName)) {
            return getMaterialData(itemId, Integer.parseInt(dataName));
        }

        Material itemMaterial = getMaterialById(itemId);
        //Use the extra-items class to search for the given item and datavalue
        return ExtraItems.getItem(itemMaterial, dataName);
    }

    /**
     * Create a new material data with the given id, and data value.
     *
     * @param itemID    id of the material to assign to the material-data object
     * @param dataValue data value to assign to the material-data object
     * @return a new material data with the given id, and data value.
     */
    public static MaterialData getMaterialData(int itemID, int dataValue) {
        return new MaterialData(Material.getMaterial(itemID), (byte) dataValue);
    }

    /**
     * Create a new material-data object based on the material and data value given.
     *
     * @param material  material to assign
     * @param dataValue datavalue for the material
     * @return materialdata object with the material, and data value assigned.
     */
    public static MaterialData getMaterialData(Material material, int dataValue) {
        return new MaterialData(material, (byte) dataValue);
    }

    /**
     * Get the itemstack associated with the given block- used when more than one material is present, IE: Doors
     *
     * @param block block to get the itemstack for
     * @return itemstack of the given block.
     */
    public static ItemStack convertBlockToItem(Block block) {
        return new ItemStack(Blocks.getBlockMaterial(block));
    }

    /**
     * Retrieve all the recipes associated with crafting the given itemstack.
     *
     * @param itemStack item to get the recipes for.
     * @return list of all the recipes used to craft the given item.
     */
    public static List<Recipe> getRecipes(ItemStack itemStack) {
        return Bukkit.getServer().getRecipesFor(itemStack);
    }

    /**
     * NOTE: UNTESTED / BUGGY
     * Show a furnace recipe to the player
     *
     * @param player        player to show the recipe to
     * @param furnaceRecipe recipe to display.
     */
    public static void showFurnaceRecipe(Player player, FurnaceRecipe furnaceRecipe) {
        Chat.message(player, Messages.recipeFurnace(furnaceRecipe.getResult(), furnaceRecipe.getInput()));
    }

    /**
     * Show the player details of the shapeless recipe
     *
     * @param player          player to show the recipe to
     * @param shapelessRecipe recipe to display
     */
    public static void showShapelessRecipe(Player player, ShapelessRecipe shapelessRecipe) {
        //Get the ingredients required for this recipe
        List<ItemStack> recipeIngredients = shapelessRecipe.getIngredientList();
        //Create a map for the recipes items
        Map<Integer, ItemStack> recipeItems = new HashMap<>();
        MinecraftPlayer minecraftPlayer = Commons.getInstance().getPlayerHandler().getData(player);

        //Put each item in their respective spot
        for (int i = 0; i < recipeIngredients.size(); i++) {
            recipeItems.put(i + 1, recipeIngredients.get(i));
        }

        //Open the workbench inventory
        InventoryView inventoryView = Inventories.openWorkbench(player);
        //Set the items in our inventory
        Inventories.setViewItems(inventoryView, recipeItems);
        minecraftPlayer.setViewingRecipe(true);
    }

    /**
     * Show the player a shaped recipe in an inventory view.
     *
     * @param player       player to show the recipe to
     * @param shapedRecipe recipe to show the player.
     */
    public static void showShapedRecipe(Player player, ShapedRecipe shapedRecipe) {
        //Get a map of all our ingredients
        Map<Character, ItemStack> itemIngredients = shapedRecipe.getIngredientMap();
        //Get the shape of our recipe
        String[] recipeShape = shapedRecipe.getShape();
        //Create a new list used to create the inventory
        Map<Integer, ItemStack> itemRecipe = new HashMap<>();
        MinecraftPlayer minecraftPlayer = Commons.getInstance().getPlayerHandler().getData(player);
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
        minecraftPlayer.setViewingRecipe(true);
    }

    /**
     * NOTE: UNTESTED / BUGGY
     * Show the recipe for an item to the player.
     *
     * @param player       player to show the recipe to
     * @param itemStack    item to get the recipe for
     * @param recipeNumber number of recipe to show, if more than 1 are available this would change- 1 by default.
     * @return true if the player was shown the given recipe, false otherwise.
     */
    public static boolean showRecipe(Player player, ItemStack itemStack, int recipeNumber) {
        //todo validate functionality, shit seems to be broken?
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return false;
        }

        List<Recipe> recipes = getRecipes(itemStack);
        int recipeCount = recipes.size();
        if (recipeCount == 0) {
            return false;
        }

        if (recipeNumber >= 0 && (recipeNumber < recipeCount)) {
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

    /**
     * Check whether or not the given item is air.
     *
     * @param itemStack item to check
     * @return true if the item is null / air, false otherwise.
     */
    public static boolean isAir(ItemStack itemStack) {
        return itemStack == null || itemStack.getType() == Material.AIR;
    }

    /**
     * Check whether or not the given item is a book.
     *
     * @param itemStack item to check
     * @return true if the item is a book, false otherwise.
     */
    public static boolean isBook(ItemStack itemStack) {
        return (getMetadata(itemStack) instanceof BookMeta);
    }

    /**
     * Check whether or not the given item is a flower
     *
     * @param item item to check.
     * @return true if the item is a flower, false otherwise.
     */
    public static boolean isFlower(ItemStack item) {
        switch (item.getType()) {
            case YELLOW_FLOWER:
            case RED_ROSE:
            case DOUBLE_PLANT:
                return true;
            default:
                return false;
        }

    }

    /**
     * Create a book with custom title, author and pages.
     *
     * @param title  title of the book
     * @param author author of the book
     * @param pages  pages to scribe the book with
     * @return the book with custom title, author, and pages assigned.
     */
    public static ItemStack makeBook(String title, String author, String... pages) {
        return makeBook(title, author, Arrays.asList(pages));
    }

    /**
     * Create a book with custom title, author, and pages.
     *
     * @param title  title of the book
     * @param author author of the book
     * @param pages  pages the book has.
     * @return the book with custom title, author, and pages assigned.
     */
    public static ItemStack makeBook(String title, String author, List<String> pages) {
        ItemStack book = makeItem(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) getMetadata(book);
        bookMeta.setTitle(title);
        bookMeta.setAuthor(author);
        bookMeta.setPages(pages);
        return setMetadata(book, bookMeta);
    }

    /**
     * Get the data value on the given itemstack.
     *
     * @param item item to get the datavalue for.
     * @return data value of the item (if available), 0 if no data.
     */
    public static int getDataValue(ItemStack item) {
        return item.getData().getData();
    }

    /**
     * @return a random dye color, of the available colors.
     */
    public static DyeColor getRandomDyeColor() {
        DyeColor[] dyeColors = DyeColor.values();
        return dyeColors[new Random().nextInt(dyeColors.length)];
    }

    private static List<Color> colors = Lists.newArrayList(Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.YELLOW, Color.WHITE, Color.TEAL);

    public static Color getRandomColor() {
        return ListUtils.getRandom(colors);
    }

    /**
     * Whether or not the item has any flags on it.
     *
     * @param item item to check for flags
     * @return true if the item has any flags, false otherwise.
     */
    public static boolean hasFlags(ItemStack item) {
        return getMetadata(item).getItemFlags().size() > 0;
    }

    /**
     * Retrieve all the flags on an item.
     *
     * @param item item to get the flags for
     * @return set of all the item flags attached to the item.
     */
    public static Set<ItemFlag> getFlags(ItemStack item) {
        return getMetadata(item).getItemFlags();
    }

}