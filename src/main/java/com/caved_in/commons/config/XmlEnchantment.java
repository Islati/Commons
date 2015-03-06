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

	@Attribute(name = "glow", required = false)
	private boolean glow = true;

	public static XmlEnchantment fromEnchant(Enchantment enchantment, int level) {
		return new XmlEnchantment(enchantment, level);
	}

	public XmlEnchantment(@Attribute(name = "name") String enchantName, @Attribute(name = "level") int level, @Attribute(name = "glow", required = false) boolean glow) {
		this.level = level;
		this.enchantName = enchantName;
		this.glow = glow;
	}

	public XmlEnchantment(Enchantment enchantment, int level) {
		this.level = level;
		enchantName = enchantment.getName();
	}

	public XmlEnchantment enchantment(Enchantment enchant) {
		this.enchantName = enchant.getName();
		return this;
	}

	public XmlEnchantment level(int level) {
		this.level = level;
		return this;
	}

	public XmlEnchantment glow(boolean glow) {
		this.glow = glow;
		return this;
	}

	public Enchantment getEnchantment() {
		return Enchantment.getByName(enchantName);
	}

	public int getLevel() {
		return level;
	}

	public boolean hasGlow() {
		return glow;
	}
}
