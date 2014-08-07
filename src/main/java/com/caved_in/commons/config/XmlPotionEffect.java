package com.caved_in.commons.config;

import com.caved_in.commons.potion.PotionType;
import com.caved_in.commons.potion.Potions;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.simpleframework.xml.Attribute;

public class XmlPotionEffect {
	@Attribute(name = "potion-type")
	private String alias = "";

	@Attribute(name = "level")
	private int level = 1;

	private PotionEffect potionEffect;
	private boolean valid = false;

	public XmlPotionEffect(@Attribute(name = "potion-type") String alias, @Attribute(name = "level") int level) {
		this.alias = alias;
		this.level = level;
		if (PotionType.isPotionType(alias)) {
			valid = true;
			this.potionEffect = Potions.getPotionEffect(PotionType.getPotionType(alias).getPotionEffectType(), level, Integer.MAX_VALUE);
		} else {
			this.potionEffect = new PotionEffect(PotionEffectType.CONFUSION, 1, 1);
		}
	}

	public boolean isValid() {
		return valid;
	}

	public PotionEffect getPotionEffect() {
		return potionEffect;
	}
}
