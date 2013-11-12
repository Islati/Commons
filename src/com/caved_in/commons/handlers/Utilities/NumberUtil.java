package com.caved_in.commons.handlers.Utilities;

import java.util.Random;

public class NumberUtil
{
	public static int getRandomInRange(int Minimum, int Maximum)
	{
		return new Random().nextInt((Maximum - Minimum) + 1) + Minimum;
	}

	public static boolean percentCheck(int Percentage)
	{
		return new Random().nextInt(101) <= Percentage;
	}
}
