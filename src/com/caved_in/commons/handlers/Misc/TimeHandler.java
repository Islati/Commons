package com.caved_in.commons.handlers.Misc;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

public class TimeHandler
{

	/**
	 * Convert a millisecond duration to a string format
	 * 
	 * @param millis
	 *            A duration to convert to a string form
	 * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
	 */
	public static String getDurationBreakdown(long millis)
	{
		if (millis < 0)
		{
			throw new IllegalArgumentException("Duration must be greater than zero!");
		}

		long days = TimeUnit.MILLISECONDS.toDays(millis);
		millis -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

		StringBuilder sb = new StringBuilder(64);
		sb.append(days);
		sb.append(" Days ");
		sb.append(hours);
		sb.append(" Hours ");
		sb.append(minutes);
		sb.append(" Minutes ");
		sb.append(seconds);
		sb.append(" Seconds");

		return (sb.toString());
	}

	public static String getDurationTrimmed(long Millis)
	{
		String Breakdown = getDurationBreakdown(Millis);
		if (StringUtils.substringBefore(Breakdown, " Days ").equalsIgnoreCase("0"))
		{
			Breakdown = StringUtils.replace(Breakdown, "0 Days ", "");
		}
		return Breakdown;
	}

	public static long getTimeInMilles(int Amount, TimeType Type)
	{
		switch (Type)
		{
		case Days:
			return (Amount * 86400000);
		case Hours:
			return (Amount * 3600000);
		case Minutes:
			return (Amount * 60000);
		case Months:
			return ((30 * 86400000) * Amount);
		case Seconds:
			return (Amount * 1000);
		case Weeks:
			return (7 * 86400000) * Amount;
		case Years:
			return (365 * 86400000) * Amount;
		default:
			return 0L;
		}
	}

	public enum TimeType
	{
		Years, Months, Weeks, Days, Hours, Minutes, Seconds
	}
}
