package com.caved_in.commons.time;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class TimeHandler {

    public static long getTimeInMilles(int amt, TimeType type) {
        GregorianCalendar calender = new GregorianCalendar();
        switch (type) {
            case DAY:
                return TimeUnit.DAYS.toMillis(amt);
            case HOUR:
                return TimeUnit.HOURS.toMillis(amt);
            case MINUTE:
                return TimeUnit.MINUTES.toMillis(amt);
            case MONTH:
                calender.add(Calendar.MONTH, amt);
                break;
            case SECOND:
                return TimeUnit.SECONDS.toMillis(amt);
            case WEEK:
                return TimeUnit.DAYS.toMillis(amt) * 7;
            case YEAR:
                calender.add(Calendar.YEAR, amt);
                break;
            case MILLESECOND:
                return amt;
            case TICK:
                return TimeUnit.SECONDS.toMillis(amt / 20) / 1000;
            default:
                return -1;
        }
        return calender.getTimeInMillis();
    }

    public static long getTimeInTicks(int amount, TimeType type) {
        switch (type) {
            case SECOND:
                return (20 * amount);
            case MINUTE:
                return ((20 * 60) * amount);
            case HOUR:
                return ((20 * 60) * 60) * amount;
            case TICK:
                return amount;
            default:
                return 0L;
        }
    }

    @Deprecated /* Broken as hell */
    public static long parseStringForDuration(String string) {
        long millesDuration = 0L;
        TimeType timeType;
        StringBuilder sb = new StringBuilder();
        char[] charArray = string.toCharArray();
        for (char character : charArray) {
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
                sb = new StringBuilder();
            }
        }
        return millesDuration;
    }

    public static String timeDurationToWords(long duration) {
        return DurationFormatUtils.formatDurationWords(duration, true, true);
    }

    public static int getSecondsFromTicks(long tickAmount) {
        return (int) (tickAmount / 20);
    }

    public static String getDurationBreakdown(long time) {
        return DurationFormatUtils.formatDurationWords(time, true, true);
    }
}
