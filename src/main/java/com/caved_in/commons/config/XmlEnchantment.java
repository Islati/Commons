package com.caved_in.commons.config;

import org.bukkit.enchantments.Enchantment;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Serializable wrapper for item enchantments.
 */
@Root(name = "enchantment")
public class XmlEnchantment {
	@Attribute(name = "name")
	private String enchantName;

	@Attribute(name = "level")
	private int level;

	public static XmlEnchantment fromEnchant(Enchantment enchantment, int level) {
		return new XmlEnchantment(enchantment, level);
	}

	public XmlEnchantment(@Attribute(name = "name") String enchantName, @Attribute(name = "level") int level) {
		this.level = level;
		this.enchantName = enchantName;
	}

	public XmlEnchantment(Enchantment enchantment, int level) {
		this.level = level;
		enchantName = enchantment.getName();
	}

	public Enchantment getEnchantment() {
		return Enchantment.getByName(enchantName);
	}

	public int getLevel() {
		return level;
	}
}
