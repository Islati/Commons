package com.caved_in.commons.config;

import com.caved_in.commons.item.Items;
import com.google.common.collect.Lists;
import com.mysql.jdbc.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Serializable wrapper for ItemStacks.
 */
@Root(name = "itemstack")
public class XmlItemStack {
	@Element(name = "item-id")
	private int id;

	@Element(name = "item-name", required = false)
	private String itemName;

	@Element(name = "item-amount", required = false)
	private int amount;

	@Element(name = "data-value", required = false)
	private int dataVal;

	@ElementList(name = "lore", entry = "line", required = false)
	private List<String> lore;

	@ElementList(name = "enchantments", entry = "enchantment", inline = true, required = false)
	private ArrayList<XmlEnchantment> enchantments;

	@Element(name = "skull-owner", required = false)
	private String skullOwner;

	@ElementList(name = "flags", entry = "flag", type = ItemFlag.class, required = false)
	private List<ItemFlag> itemFlags = null;

	private ItemStack itemStack;

	public static XmlItemStack fromItem(ItemStack item) {
		return new XmlItemStack(item);
	}

	public XmlItemStack(@Element(name = "item-id") int id, @Element(name = "item-amount", required = false) int amount, @Element(name = "data-value", required = false) int dataVal, @Element(name = "item-name", required = false) String itemName, @ElementList(name = "lore", entry = "line", required = false) ArrayList<String> lore, @ElementList(name = "enchantments", entry = "enchantment", inline = true, required = false) ArrayList<XmlEnchantment> enchantments, @Element(name = "skull-owner", required = false) String skullOwner, @ElementList(name = "flags", entry = "flag", type = ItemFlag.class, required = false) List<ItemFlag> flags) {
		this.id = id;
		this.dataVal = dataVal;
		this.itemName = itemName;
		this.lore = lore;
		this.enchantments = enchantments;
		this.amount = amount;
		this.skullOwner = skullOwner;
		this.itemFlags = flags;
	}

	public XmlItemStack(ItemStack item) {
		id = item.getTypeId();

		if (Items.isArmor(item) || Items.isWeapon(item)) {
			dataVal = Items.getDataValue(item);
		} else {
			dataVal = item.getDurability();
		}

		if (Items.hasName(item)) {
			itemName = Items.getName(item);
		} else {
			itemName = Items.getFormattedMaterialName(item);
		}

		if (Items.hasLore(item)) {
			lore = Items.getLore(item);
		}
		//If the item has enchantments, then add them to be serialized
		if (Items.hasEnchantments(item)) {
			enchantments = new ArrayList<>();
			for (Map.Entry<Enchantment, Integer> enchantment : item.getEnchantments().entrySet()) {
				enchantments.add(new XmlEnchantment(enchantment.getKey(), enchantment.getValue()));
			}
		}

		if (Items.hasFlags(item)) {
			itemFlags = Lists.newArrayList(Items.getFlags(item));
		}

		int itemAmount = item.getAmount();

		if (itemAmount > 1) {
			amount = itemAmount;
		}

		// If the item is a players skull, then we'll save the owner name; so we can load it back!
		if (Items.isPlayerSkull(item)) {
			skullOwner = ((SkullMeta) item.getItemMeta()).getOwner();
		}

		itemStack = item.clone();
	}

	public ItemStack getItemStack() {
		if (itemStack == null) {
			Material itemMaterial = Material.getMaterial(id);

			//If the item is a human skull/player skull then we'll re-assign the owner
			if (itemMaterial == Material.SKULL_ITEM && dataVal == 3) {
				//Sweet! There's a player skull, now assure we've got an owner for the skull.
				if (!StringUtils.isNullOrEmpty(skullOwner)) {
					itemStack = Items.getSkull(skullOwner);
				}
			//If there's a data value attached to this item, then create the item as such
			} else if (dataVal > 0) {
				itemStack = Items.makeItem(itemMaterial, dataVal);
			} else {
				itemStack = Items.makeItem(itemMaterial);
			}

			if (itemName != null && !itemName.isEmpty()) {
				Items.setName(itemStack, itemName);
			}

			if (lore != null && lore.size() > 0) {
				Items.setLore(itemStack, lore);
			}

			if (enchantments != null && enchantments.size() > 0) {
				for (XmlEnchantment enchantment : enchantments) {
					Items.addEnchantment(itemStack, enchantment.getEnchantment(), enchantment.getLevel());
				}
			}

			if (amount > 1) {
				itemStack.setAmount(amount);
			}
		}
		return itemStack;
	}

	public String getItemName() {
		return itemName;
	}

	public int getId() {
		return id;
	}

	public int getAmount() {
		return amount;
	}

	public int getDataVal() {
		return dataVal;
	}

	public List<String> getLore() {
		return lore;
	}

	public Material getMaterial() {
		return getItemStack().getType();
	}

	public boolean isPlayerSkull() {
		return Items.isPlayerSkull(getItemStack());
	}

	public String getSkullOwner() {
		return skullOwner;
	}
}
