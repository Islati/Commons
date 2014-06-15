package com.caved_in.commons.utilities;

import java.util.Random;

public class ArrayUtils {

	public static void shuffleArray(Object[] objects) {
		int index;
		Object temp;
		Random random = new Random();
		for (int i = objects.length - 1; i > 0; i--) {
			index = random.nextInt(i + 1);
			temp = objects[index];
			objects[index] = objects[i];
			objects[i] = temp;
		}
	}
}
