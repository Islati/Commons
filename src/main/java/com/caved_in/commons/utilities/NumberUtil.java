package com.caved_in.commons.utilities;

import java.util.Random;

public class NumberUtil {
    private static Random random = new Random();

    public static int getRandomInRange(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static double getRandomInRange(double min, double max) {
        double range = max - min;
        double scaled = random.nextDouble() * range;
        double shifted = scaled + min;
        return shifted; // == (rand.nextDouble() * (max-min)) + min;
    }

    public static boolean percentCheck(int percent) {
        return random.nextInt(101) <= percent;
    }

    public static float percentOf(int num, int target) {
        return num * 100.0f / target;
    }

    public static boolean isInRange(int num, int min, int max) {
        return num >= min && num <= max;
    }
}
