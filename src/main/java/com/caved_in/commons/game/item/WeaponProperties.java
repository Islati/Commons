package com.caved_in.commons.game.item;

import com.caved_in.commons.game.gadget.GadgetProperties;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.yml.Path;
import lombok.ToString;

import java.io.File;

@ToString(of = {"damageMin", "damageMax"}, callSuper = true)
/**
 * An extension of the GadgetProperties class, it adds a minimum and maximum damage range for the weapon.
 * Damage range (min-max) has no integrated function though can be used inside plugins utilizing the weapons.
 */
public class WeaponProperties extends GadgetProperties {
    @Path("damage-min")
    private double damageMin = 0;

    @Path("damage-max")
    private double damageMax = 0;

    public WeaponProperties() {

    }

    public WeaponProperties(File file) {
        super(file);
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

    public boolean hasDamageRange() {
        return damageMin > 0 && damageMax > 0;
    }
}
