package com.caved_in.commons.config;

import lombok.ToString;
import org.bukkit.enchantments.Enchantment;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Serializable wrapper for item enchantments.
 */
@Root(name = "enchantment")
@ToString(of = {"enchantName", "level", "glow"})
public class SerializableEnchantment {
    @Attribute(name = "name")
    private String enchantName;

    @Attribute(name = "level")
    private int level;

    @Attribute(name = "glow", required = false)
    private boolean glow = true;

    public static SerializableEnchantment fromEnchant(Enchantment enchantment, int level) {
        return new SerializableEnchantment(enchantment, level);
    }

    public SerializableEnchantment(@Attribute(name = "name") String enchantName, @Attribute(name = "level") int level, @Attribute(name = "glow", required = false) boolean glow) {
        this.level = level;
        this.enchantName = enchantName;
        this.glow = glow;
    }

    public SerializableEnchantment(Enchantment enchantment, int level) {
        this.level = level;
        enchantName = enchantment.getName();
    }

    public SerializableEnchantment() {

    }

    public SerializableEnchantment enchantment(Enchantment enchant) {
        this.enchantName = enchant.getName();
        return this;
    }

    public SerializableEnchantment level(int level) {
        this.level = level;
        return this;
    }

    public SerializableEnchantment glow(boolean glow) {
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
