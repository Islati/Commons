package com.caved_in.commons.potions;

import org.bukkit.potion.PotionEffectType;

public enum PotionType {
	//Strength Potions
	STRENGTH_LVL_1(PotionEffectType.INCREASE_DAMAGE,1),
	STRENGTH_LVL_2(PotionEffectType.INCREASE_DAMAGE,2),
	//Speed Potions
	SPEED_LVL_1(PotionEffectType.SPEED,1),
	SPEED_LVL_2(PotionEffectType.SPEED,2),
	SPEED_LVL_3(PotionEffectType.SPEED,3),
	//Invisibility
	INVISIBILITY(PotionEffectType.INVISIBILITY,1),
	//Nausea
	NAUSEA_LVL_1(PotionEffectType.CONFUSION,1),
	//Blindness
	BLINDNESS_LVL_1(PotionEffectType.BLINDNESS,1),
	BLINDNESS_LVL_2(PotionEffectType.BLINDNESS,2),
	BLINDNESS_LVL_3(PotionEffectType.BLINDNESS,3),
	//Jump
	JUMP_LVL_1(PotionEffectType.JUMP,1),
	JUMP_LVL_2(PotionEffectType.JUMP,2),
	//Slow
	SLOW_LVL_1(PotionEffectType.SLOW,1),
	SLOW_LVL_2(PotionEffectType.SLOW,2);

	private PotionEffectType potionEffectType;
	private int amplification;

	PotionType(PotionEffectType potionEffectType, int amplification) {
		this.potionEffectType = potionEffectType;
		this.amplification = amplification;
	}

	public PotionEffectType getPotionEffectType() {
		return potionEffectType;
	}

	public int getAmplification() {
		return amplification;
	}
}
