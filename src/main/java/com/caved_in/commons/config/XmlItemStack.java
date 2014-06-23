package com.caved_in.commons.config;

import com.caved_in.commons.item.Items;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	private ItemStack itemStack;

	public XmlItemStack(@Element(name = "item-id") int id, @Element(name = "item-amount", required = false) int amount, @Element(name = "data-value", required = false) int dataVal, @Element(name = "item-name", required = false) String itemName, @ElementList(name = "lore", entry = "line", required = false) ArrayList<String> lore, @ElementList(name = "enchantments", entry = "enchantment", inline = true, required = false) ArrayList<XmlEnchantment> enchantments) {
		this.id = id;
		this.dataVal = dataVal;
		this.itemName = itemName;
		this.lore = lore;
		this.enchantments = enchantments;
		this.amount = amount;
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

		lore = Items.getLore(item);
		//If the item has enchantments, then add them to be serialized
		if (Items.hasEnchants(item)) {
			enchantments = new ArrayList<>();
			for (Map.Entry<Enchantment, Integer> enchantment : item.getEnchantments().entrySet()) {
				enchantments.add(new XmlEnchantment(enchantment.getKey(), enchantment.getValue()));
			}
		}
		//Clone the item!

		int itemAmount = itemStack.getAmount();

		if (itemAmount > 1) {
			amount = itemAmount;
		}

		itemStack = item.clone();
	}

	public ItemStack getItemStack() {
		if (itemStack == null) {
			Material itemMaterial = Material.getMaterial(id);
			//If there's a data value attached to this item, then create the item as such
			if (dataVal > 0) {
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
}
