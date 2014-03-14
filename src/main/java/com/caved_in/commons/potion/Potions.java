package com.caved_in.commons.potion;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Potions {

	public static PotionEffect getPotionEffect(PotionEffectType potionEffectType, int amplification, int durationInTicks) {
		return new PotionEffect(potionEffectType, durationInTicks, amplification);
	}

}
