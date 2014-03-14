package com.caved_in.commons.item;

import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemBuilder {
	private Material material;
	private MaterialData materialData;

	private String name;

	private int amount = 1;
	private short durability = -101;

	private List<String> lore = new ArrayList<>();
	private List<EnchantWrapper> enchantments = new ArrayList<>();

	public ItemBuilder(Material material) {
		this.material = material;
	}

	public ItemBuilder(Material material, int amount) {
		this.material = material;
		this.amount = amount;
	}

	public ItemBuilder amount(int amount) {
		this.amount = amount;
		return this;
	}

	public ItemBuilder withName(String name) {
		this.name = StringUtil.formatColorCodes(name);
		return this;
	}

	public ItemBuilder withLore(String... lore) {
		Collections.addAll(this.lore, lore);
		return this;
	}

	public ItemBuilder withDurability(short durability) {
		this.durability = durability;
		return this;
	}

	public ItemBuilder withEnchantment(Enchantment enchantment, int level) {
		return withEnchantment(enchantment, level, true);
	}

	public ItemBuilder withEnchantment(Enchantment enchantment, int level, boolean glow) {
		enchantments.add(new EnchantWrapper(enchantment, level, glow));
		return this;
	}

	public ItemBuilder withMaterialData(MaterialData materialData) {
		this.materialData = materialData;
		return this;
	}

	public ItemStack toItemStack() {
		ItemStack itemStack = new ItemStack(material, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		//If the name for the builders been set then set the name on the item
		if (name != null && !name.isEmpty()) {
			itemMeta.setDisplayName(name);
		}
		//Check for lore and set the lore
		if (!lore.isEmpty()) {
			itemMeta.setLore(lore);
		}
		//If there's any enchantments added to the itembuilder, add them to the item
		if (!enchantments.isEmpty()) {
			for (EnchantWrapper enchantWrapper : enchantments) {
				itemMeta.addEnchant(enchantWrapper.getEnchantment(), enchantWrapper.getLevel(), enchantWrapper.isItemGlow());
			}
		}
		//Set the item metadata
		itemStack.setItemMeta(itemMeta);
		//Check if the durability has been changed, if so set it
		if (durability != -101) {
			itemStack.setDurability(durability);
		}
		//Check if any materialData has been set
		if (materialData != null) {
			itemStack.setData(materialData);
		}
		//Return our itemstack
		return itemStack;
	}
}
