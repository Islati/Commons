package com.caved_in.commons.potions;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionHandler {

	public static PotionEffect getPotionEffect(PotionEffectType potionEffectType, int amplification, int durationInTicks) {
		return new PotionEffect(potionEffectType, durationInTicks, amplification);
	}

	public static PotionEffect getPotionEffect(PotionType potionType, int durationInTicks) {
		return new PotionEffect(potionType.getPotionEffectType(),durationInTicks, potionType.getAmplification());
	}

}
