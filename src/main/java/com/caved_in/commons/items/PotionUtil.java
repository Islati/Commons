package com.caved_in.commons.items;

import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class PotionUtil {

	/**
	 * Create a potion effect
	 *
	 * @param effectType Type of our potion
	 * @param Duration   how long the effect should/will last for
	 * @param Strength   the strength(amplification) of the potion
	 * @return a potion effect object based on the arguments passed
	 */
	public static PotionEffect getPotionEffect(PotionEffectType effectType, int Duration, int Strength) {
		return new PotionEffect(effectType, Duration, Strength);
	}

	/**
	 * Create a potion effect with a duration between the minimum and maximum range
	 *
	 * @param effectType      Type of our potion
	 * @param minimumDuration minimum duration the effect can last for
	 * @param maximumDuration maximum duration the effect can last for
	 * @param strength        the strength(amplification) of the potion
	 * @return a potion effect object based on the arguments passed
	 */
	public static PotionEffect getPotionEffect(PotionEffectType effectType, int minimumDuration, int maximumDuration, int strength) {
		return new PotionEffect(effectType, NumberUtil.getRandomInRange(minimumDuration, maximumDuration), strength);
	}

	public static void addPotionEffects(Player player, PotionEffect... potionEffects) {
		for (PotionEffect Effect : potionEffects) {
			if (!player.hasPotionEffect(Effect.getType())) {
				player.addPotionEffect(Effect);
			}
		}
	}

	public static void addPotionEffects(Player player, List<PotionEffect> potionEffects) {
		for (PotionEffect Effect : potionEffects) {
			if (!player.hasPotionEffect(Effect.getType())) {
				player.addPotionEffect(Effect);
			}
		}
	}
}
