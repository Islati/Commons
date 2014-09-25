package com.caved_in.commons.config;

import com.caved_in.commons.potion.PotionType;
import com.caved_in.commons.potion.Potions;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.simpleframework.xml.Attribute;

/**
 * Serializable wrapper for Potion effects.
 */
public class XmlPotionEffect {
	@Attribute(name = "potion-type")
	private String alias = "";

	@Attribute(name = "level")
	private int level = 1;

	@Attribute(name = "duration", required = false)
	private int duration = 0;

	private PotionEffect potionEffect;
	private boolean valid = false;

	public XmlPotionEffect(@Attribute(name = "potion-type") String alias, @Attribute(name = "level") int level, @Attribute(name = "duration", required = false) int duration) {
		this.alias = alias;
		this.level = level;
		this.duration = duration;
		if (PotionType.isPotionType(alias)) {
			valid = true;
			this.potionEffect = Potions.getPotionEffect(PotionType.getPotionType(alias).getPotionEffectType(), level, duration > 0 ? duration : Integer.MAX_VALUE);
		} else {
			this.potionEffect = new PotionEffect(PotionEffectType.CONFUSION, 1, 1);
		}
	}

	public XmlPotionEffect(PotionEffect effect) {
		this.alias = effect.getType().getName();
		this.level = effect.getAmplifier();
		this.duration = effect.getDuration();
	}

	public boolean isValid() {
		return valid;
	}

	public PotionEffect getPotionEffect() {
		return potionEffect;
	}

	public int getDuration() {
		return duration;
	}

	public int getLevel() {
		return level;
	}
}
