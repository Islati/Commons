package com.caved_in.commons.game.item;

import com.caved_in.commons.utilities.NumberUtil;

public class WeaponProperties {
	private int damageMin;
	private int damageMax;

	public WeaponProperties() {

	}

	public WeaponProperties damage(int min, int max) {
		this.damageMin = min;
		this.damageMax = max;
		return this;
	}

	public double getDamage() {
		return NumberUtil.getRandomInRange(damageMin, damageMax);
	}
}
