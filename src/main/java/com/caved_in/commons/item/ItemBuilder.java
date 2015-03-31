package com.caved_in.commons.item;

import com.caved_in.commons.config.XmlEnchantment;
import com.caved_in.commons.exceptions.ItemCreationException;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.utilities.SneakyThrow;
import com.caved_in.commons.utilities.StringUtil;
import com.google.common.collect.Lists;
import com.mysql.jdbc.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.*;

public class ItemBuilder {
	private Material material;
	private MaterialData materialData;

	private String name;

	private int amount = 1;
	private short durability = -101;

	private List<String> lore = new ArrayList<>();
	private List<EnchantWrapper> enchantments = new ArrayList<>();

	private Attributes attributes;
	private List<Attributes.Attribute> attributeList = new ArrayList<>();

	/* Whether or not the item is unbreakable, by default false, as most items break! */
	private boolean unbreakable = false;

	public static ItemBuilder of(Material material) {
		return new ItemBuilder(material);
	}

	public static ItemBuilder of(ItemStack item) {
		return new ItemBuilder(item);
	}

	public static ItemBuilder of(MaterialData data) {
		return of(data.toItemStack(1));
	}

	public ItemBuilder(Material material) {
		this.material = material;
	}

	public ItemBuilder(Material material, int amount) {
		this.material = material;
		this.amount = amount;
	}

	public ItemBuilder(ItemStack base) {
		this.material = base.getType();
		this.materialData = base.getData();
		this.durability = (short) Items.getDataValue(base);

		if (Items.hasLore(base)) {
			this.lore = Items.getLore(base);
		}
		
		this.enchantments = Lists.newArrayList(Items.getEnchantments(base));
		this.amount = base.getAmount();
		this.name = Items.getName(base);
	}

	public ItemBuilder() {
	}

	public ItemBuilder addAttribute(String name, Attributes.AttributeType attributeType, Attributes.Operation operation, int amount) {
		attributeList.add(Attributes.Attribute.newBuilder().name(name).type(attributeType).operation(operation).amount(amount).build());
		return this;
	}

	public ItemBuilder addAttribute(Attributes.Attribute attr) {
		attributeList.add(attr);
		return this;
	}

	public ItemBuilder addAttribute(Optional<Attributes.Attribute> attr) {
		if (attr.isPresent()) {
			return addAttribute(attr.get());
		}
		return this;
	}

	public ItemBuilder amount(int amount) {
		this.amount = amount;
		return this;
	}

	public ItemBuilder name(String name) {
		this.name = StringUtil.formatColorCodes(name);
		return this;
	}

	public ItemBuilder lore(String... lore) {
		Collections.addAll(this.lore, lore);
		return this;
	}

	public ItemBuilder lore(List<String> lore) {
		this.lore.addAll(lore);
		return this;
	}

	public ItemBuilder durability(short durability) {
		this.durability = durability;
		return this;
	}

	public ItemBuilder enchantment(Enchantment enchantment, int level) {
		return enchantment(enchantment, level, true);
	}

	public ItemBuilder enchantment(Enchantment enchantment, int level, boolean glow) {
		enchantments.add(new EnchantWrapper(enchantment, level, glow));
		return this;
	}

	public ItemBuilder enchantments(Collection<XmlEnchantment> enchants) {
		for (XmlEnchantment e : enchants) {
			enchantments.add(new EnchantWrapper(e.getEnchantment(), e.getLevel(), e.hasGlow()));
		}
		return this;
	}

	public ItemBuilder materialData(MaterialData materialData) {
		this.materialData = materialData;
		return this;
	}

	public ItemBuilder unbreakable() {
		unbreakable = true;
		return this;
	}

	public ItemBuilder breakable() {
		unbreakable = false;
		return this;
	}

	public ItemStack item() {
		if (material == null || material == Material.AIR) {
			SneakyThrow.sneaky(new ItemCreationException("Unable to create an item with air (or null) materials"));
		}

		ItemStack itemStack = new ItemStack(material, amount);

		ItemMeta itemMeta = itemStack.getItemMeta();

		//If the name for the builders been set then set the name on the item
		if (!StringUtils.isNullOrEmpty(name)) {
			itemMeta.setDisplayName(name);
		}
		//Check for lore and set the lore
		if (lore != null && !lore.isEmpty()) {
			itemMeta.setLore(StringUtil.formatColorCodes(lore));
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

		//Set the items breakability status.
		itemStack.getItemMeta().spigot().setUnbreakable(unbreakable);


		/*
		If there's been any attributes added to the item,
		then we can go ahead and add them all in!
		 */
		if (attributeList.size() > 0) {
			if (!Plugins.hasProtocolLib()) {
				SneakyThrow.sneaky(new ItemCreationException("Unable to apply attributes to the items as it required ProtocolLib as a dependancy!"));
			}

			attributes = new Attributes(itemStack);

			for (Attributes.Attribute itemAttribute : attributeList) {
				attributes.add(itemAttribute);
			}

			itemStack = attributes.getStack();
		}

		//Return our itemstack
		return itemStack;
	}
}
