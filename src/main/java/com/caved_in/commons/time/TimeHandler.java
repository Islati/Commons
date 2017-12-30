package com.caved_in.commons.time;

import org.apache.commons.lang.time.DurationFormatUtils;

import java.text.SimpleDateFormat;
import java.util.*;
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
            case MILLISECOND:
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
    public static String timeDurationToWords(long duration) {
        return DurationFormatUtils.formatDurationWords(duration, true, true);
    }

    public static String trimDurationDifferenceToWords(long start, long finish) {
        return trimNowToWords(finish - start);
    }

    public static String trimNowToWords(long time) {
        return trimDurationDifferenceToWords(time,System.currentTimeMillis());
    }

    public static int getSecondsFromTicks(long tickAmount) {
        return (int) (tickAmount / 20);
    }

    public static String getDurationBreakdown(long time) {
        return DurationFormatUtils.formatDurationWords(time, true, true);
    }

    public static Date getDateFromTimeStamp(long timestamp) {
        return new Date(timestamp);
    }

    private static SimpleDateFormat numericDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static String dateToStringNumeric(Date date) {
        return numericDateFormat.format(date);
    }

    private static SimpleDateFormat wordDateFormat = new SimpleDateFormat("MMMM dd, yyyyy");

    public static String dateToStringWords(Date date) {
        return wordDateFormat.format(date);
    }
}
