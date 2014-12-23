package com.caved_in.commons.utilities;

import org.bukkit.entity.Player;

import java.util.Random;
import java.util.UUID;

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


	/**
	 * Retrieve the uuids for the given players. Used internally.
	 *
	 * @param players players to get the uuids of.
	 * @return the uuids for the players.
	 */
	public static UUID[] getIdArray(Player[] players) {
		UUID[] ids = new UUID[players.length - 1];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = players[i].getUniqueId();
		}
		return ids;
	}
}
