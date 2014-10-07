package com.caved_in.commons.utilities;

import java.util.Random;

public class NumberUtil {
	public static int getRandomInRange(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	public static boolean percentCheck(int percent) {
		return new Random().nextInt(101) <= percent;
	}

	public static float percentOf(int num, int target) {
		return num * 100.0f / target;
	}

	public static boolean isInRange(int num, int min, int max) {
		return num >= min && num <= max;
	}
}
