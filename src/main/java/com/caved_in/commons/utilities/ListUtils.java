package com.caved_in.commons.utilities;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class ListUtils {
	private static final Random rand = new Random();

	/**
	 * Joins all of the elements in the list together with a separator string.
	 *
	 * @param sep    The string to use as a separator.
	 * @param values The values.
	 * @return A string of elements with the separator between them.
	 */
	public static String implode(String sep, List<?> values) {
		if (values.size() == 0) {
			return "";
		}

		StringBuilder builder = new StringBuilder();

		builder.append(values.get(0).toString());

		for (int i = 1; i < values.size(); ++i) {
			builder.append(sep);
			builder.append(values.get(i).toString());
		}

		return builder.toString();
	}

	/**
	 * Gets a random entry from the list.
	 *
	 * @param items The list to use.
	 * @return A random entry from the list.
	 */
	public static <T> T getRandom(List<T> items) {
		return items.get(rand.nextInt(items.size()));
	}

	/**
	 * Sums all of the numbers in the list.
	 *
	 * @param numbers The list
	 * @return The sum.
	 */
	public static int sumInts(Collection<Integer> numbers) {
		int result = 0;

		for (Integer number : numbers) {
			result += number;
		}

		return result;
	}

	/**
	 * Sums all of the numbers in the list.
	 *
	 * @param numbers The list
	 * @return The sum.
	 */
	public static long sumLongs(Collection<Long> numbers) {
		long result = 0;

		for (Long number : numbers) {
			result += number;
		}

		return result;
	}

	/**
	 * Sums all of the numbers in the list.
	 *
	 * @param numbers The list
	 * @return The sum.
	 */
	public static double sumDoubles(Collection<Double> numbers) {
		double result = 0;

		for (Double number : numbers) {
			result += number;
		}

		return result;
	}

	/**
	 * Sums all of the numbers in the list.
	 *
	 * @param numbers The list
	 * @return The sum.
	 */
	public static float sumFloats(Collection<Float> numbers) {
		float result = 0;

		for (Float number : numbers) {
			result += number;
		}

		return result;
	}

	public static <T> List<T> removeDuplicates(List<T> list) {
		return Lists.newArrayList(Sets.newHashSet(list));
	}
}
