package com.caved_in.commons.utilities;

import java.util.Random;

public class Scaler {
    private static final Random random = new Random();

    public final double minValue;
    public final double maxValue;
    public final int minLvl;
    public final int maxLvl;

    public Scaler(int minLvl, double minValue, int maxLvl, double maxValue) {
        this.minLvl = minLvl;
        this.minValue = minValue;
        this.maxLvl = maxLvl;
        this.maxValue = maxValue;
    }

    public double scale(double level) {
        if (level <= minLvl) {
            return minValue;
        }
        double perStep = (maxValue - minValue) / (maxLvl - minLvl);
        double steps = level - minLvl;
        if (perStep < 0.0) {
            return Math.max(maxValue, minValue + perStep * steps);
        } else {
            return Math.min(maxValue, minValue + perStep * steps);
        }
    }

    public boolean isRandomChance(double level) {
        return random.nextDouble() < scale(level) / 100.0D;
    }
}
