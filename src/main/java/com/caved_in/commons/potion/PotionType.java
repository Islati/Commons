package com.caved_in.commons.potion;

import org.bukkit.potion.PotionEffectType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PotionType {
	STRENGTH(PotionEffectType.INCREASE_DAMAGE, "strength", "damage", "power"),
	INVISIBILITY(PotionEffectType.INVISIBILITY, "invisibility", "invisible", "hidden", "hide"),
	NAUSEA(PotionEffectType.CONFUSION, "confusion", "nausea", "sickness"),
	SPEED(PotionEffectType.SPEED, "speed", "fastwalk", "haste"),
	JUMP(PotionEffectType.JUMP, "jump", "jumping", "bunnyhop"),
	FAST_DIGGING(PotionEffectType.FAST_DIGGING, "digspeed", "fastdig", "mininghaste", "minerhaste", "minehaste"),
	SLOW_DIGGING(PotionEffectType.SLOW_DIGGING, "slowdigging", "slowdig", "minedehaste", "slow_digging"),
	HEAL(PotionEffectType.HEAL, "heal", "healdamage"),
	HEALTH_BOOST(PotionEffectType.HEALTH_BOOST, "healthboost", "hpbonus", "bonushp", "health_bonus", "increasedhealth", "increasedhp", "increasehp"),
	DAMAGE_RESISTANCE(PotionEffectType.DAMAGE_RESISTANCE, "damageresistance", "damageresist", "defence", "defbuff", "increasedefense", "defenceincrease"),
	BLINDNESS(PotionEffectType.BLINDNESS, "blindness", "blind", "sight"),
	WATER_BREATHING(PotionEffectType.WATER_BREATHING, "respiration", "oxygen", "waterbreathing", "water_breathing", "diverbreath"),
	FIRE_RESISTANCE(PotionEffectType.FIRE_RESISTANCE, "flamedefense", "fireresistance", "firearmor", "firedef", "flamedef", "protectfire", "fireprotection"),
	SLOWNESS(PotionEffectType.SLOW, "slow", "slowness"),
	INSTANT_DAMAGE(PotionEffectType.HARM, "harm", "instantdamage", "hurt"),
	WEAKNESS(PotionEffectType.WEAKNESS, "weakness", "weak"),
	POISON(PotionEffectType.POISON, "poison"),
	WITHER(PotionEffectType.WITHER, "wither", "withereffect"),
	SATURATION(PotionEffectType.SATURATION, "saturation"),
	ABSORPTION(PotionEffectType.ABSORPTION, "absorbtion"),
	NIGHT_VISION(PotionEffectType.NIGHT_VISION, "nightvision", "nighthawk", "nightsee", "nightowl");

	private static Map<String, PotionType> potionTypes = new HashMap<>();

	static {
		for (PotionType potionType : EnumSet.allOf(PotionType.class)) {
			for (String identifier : potionType.identifiers) {
				potionTypes.put(identifier, potionType);
			}
		}
	}

	private PotionEffectType potionEffectType;
	private String[] identifiers;

	PotionType(PotionEffectType potionEffectType, String... identifiers) {
		this.potionEffectType = potionEffectType;
		this.identifiers = identifiers;
	}

	public PotionEffectType getPotionEffectType() {
		return potionEffectType;
	}

	public String[] getIdentifiers() {
		return this.identifiers;
	}

	public static boolean isPotionType(String search) {
		return potionTypes.containsKey(search.toLowerCase());
	}

	public static PotionType getPotionType(String search) {
		return potionTypes.get(search.toLowerCase());
	}
}
