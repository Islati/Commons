package com.caved_in.commons.utilities;

import java.util.Random;

public class NumberUtil {
	public static int getRandomInRange(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	public static boolean percentCheck(int percent) {
		return new Random().nextInt(101) <= percent;
	}
}
