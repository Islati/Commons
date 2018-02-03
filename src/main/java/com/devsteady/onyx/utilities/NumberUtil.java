package com.devsteady.onyx.utilities;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NumberUtil {
    private static Random random = new Random();

    private static DecimalFormat format = new DecimalFormat();

    static {
        format.setRoundingMode(RoundingMode.CEILING);
    }

    public static int getRandomInRange(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static double randomDouble(double min, double max) {
        return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
    }

    public static double round(double num, int decimalPlaces) {
        StringBuilder formatString = new StringBuilder("#.");
        for (int i = 0; i < decimalPlaces; i++) {
            formatString.append("#");
        }

        format.applyPattern(formatString.toString());
        return Double.parseDouble(format.format(num));
    }

    public static boolean percentCheck(int percent) {
        return random.nextInt(101) <= percent;
    }

    public static boolean percentCheck(double percent) {
        return random.nextInt(101) <= percent;
    }

    public static float percentOf(int num, int target) {
        return num * 100.0f / target;
    }

    public static boolean isInRange(int num, int min, int max) {
        return num >= min && num <= max;
    }
}
