package com.caved_in.commons.potion;

import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CyclicPotion {

    private int durMin;
    private int durMax;
    private int ampMin;
    private int ampMax;

    private PotionEffectType type;

    private PotionEffect effect;

    /**
     * Create a new cyclic potion.
     *
     * @param type         type of potion to create
     * @param durationMin  minimum duration the potion effect will last (in ticks)
     * @param durationMax  maximum duration the potion can last (in ticks)
     * @param amplifierMin minimum level of the potion
     * @param amplifierMax maximum level of the potion
     */
    public CyclicPotion(PotionEffectType type, int durationMin, int durationMax, int amplifierMin, int amplifierMax) {
        this.type = type;
        this.durMin = durationMin;
        this.durMax = durationMax;
        this.ampMin = amplifierMin;
        this.ampMax = amplifierMax;
    }

    public CyclicPotion effect(PotionEffectType type) {
        this.type = type;
        return this;
    }

    public CyclicPotion randomize() {
        effect = Potions.getPotionEffect(type, NumberUtil.getRandomInRange(ampMin, ampMax), NumberUtil.getRandomInRange(durMin, durMax));
        return this;
    }

    public PotionEffect effect() {
        return effect;
    }
}
