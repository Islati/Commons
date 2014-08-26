package com.caved_in.commons.utilities;

import java.util.Random;

public class ArrayUtils {
	private static final Random random = new Random();

	public static <T> T getRandom(T[] items) {
		return items[random.nextInt(items.length)];
	}

	public static void shuffleArray(Object[] objects) {
		int index;
		Object temp;
		for (int i = objects.length - 1; i > 0; i--) {
			index = random.nextInt(i + 1);
			temp = objects[index];
			objects[index] = objects[i];
			objects[i] = temp;
		}
	}
}
