package com.caved_in.commons.time;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.TimeUnit;

public class TimeHandler {

	/**
	 * Convert a millisecond duration to a string format
	 *
	 * @param millis A duration to convert to a string form
	 * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
	 */
	public static String getDurationBreakdown(long millis) {
		if (millis < 0) {
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

	public static String getDurationTrimmed(long Millis) {
		String Breakdown = getDurationBreakdown(Millis);
		if (StringUtils.substringBefore(Breakdown, " Days ").equalsIgnoreCase("0")) {
			Breakdown = StringUtils.replace(Breakdown, "0 Days ", "");
		}
		return Breakdown;
	}

	public static long getTimeInMilles(int Amount, TimeType Type) {
		switch (Type) {
			case DAY:
				return (Amount * 86400000);
			case HOUR:
				return (Amount * 3600000);
			case MINUTE:
				return (Amount * 60000);
			case MONTH:
				return ((30 * 86400000) * Amount);
			case SECOND:
				return (Amount * 1000);
			case WEEK:
				return (7 * 86400000) * Amount;
			case YEAR:
				return (365 * 86400000) * Amount;
			case MILLESECOND:
				return 1L;
			default:
				return 0L;
		}
	}

	public static long getTimeInTicks(int amount, TimeType type) {
		switch (type) {
			case SECOND:
				return (20 * amount);
			case MINUTE:
				return ((20 * 60) * amount);
			case HOUR:
				return ((20 * 60) * 60) * amount;
			default:
				return 0L;
		}
	}

	public static long parseStringForDuration(String string) {
		long millesDuration = 0L;
		TimeType timeType;
		StringBuilder sb = new StringBuilder();
		for (char character : string.toCharArray()) {
			String s = String.valueOf(character);
			/*If it's a number that's being parsed then add it to the string builder
			so we can create a whole number (ie. 1m22d would only equal 1 month 2 days otherwise
			as it wouldn't concat the string*/
			if (StringUtils.isNumeric(s)) {
				sb.append(s);
			} else if (TimeType.isTimeType(s)) {
				//Parse the time type and the time amount, then calculate it to a duration in milleseconds
				timeType = TimeType.getTimeType(s);
				int timeAmount = Integer.parseInt(sb.toString());
				millesDuration += getTimeInMilles(timeAmount, timeType);
			}
		}
		return millesDuration;
	}

	public static int getSecondsFromTicks(long tickAmount) {
		return (int) (tickAmount / 20);
	}
}
