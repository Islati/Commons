package com.caved_in.commons.game.item;

import com.caved_in.commons.game.gadget.GadgetProperties;
import com.caved_in.commons.utilities.NumberUtil;
import lombok.ToString;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "weapon-properties")
@ToString(of = {"damageMin", "damageMax"}, callSuper = true)
public class WeaponProperties extends GadgetProperties {
	@Element(name = "damage-min")
	private double damageMin;

	@Element(name = "damage-max")
	private double damageMax;

	public WeaponProperties() {

	}

	public WeaponProperties(@Element(name = "durability") int durability, @Element(name = "breakable") boolean isBreakable, @Element(name = "droppable") boolean isDroppable, @Element(name = "damage-min") double damageMin, @Element(name = "damage-max") double damageMax) {
		super(durability, isBreakable, isDroppable);
		/* Apply the damage to the instance of weapon properties */
		damage(damageMin, damageMax);
	}

	public WeaponProperties damage(double min, double max) {
		this.damageMin = min;
		this.damageMax = max;
		return this;
	}

	public double getDamage() {
		return NumberUtil.getRandomInRange(damageMin, damageMax);
	}

	public double getMinDamage() {
		return damageMin;
	}

	public double getMaxDamage() {
		return damageMax;
	}
}
