package com.caved_in.commons.time;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class CalenderWrapper {
	private Map<TimeType, Integer> times = new HashMap<>();
	private GregorianCalendar calender = new GregorianCalendar();

	public void addTimeAmount(TimeType type, int length) {
		times.put(type, length);
	}

	public void parseTimes() {
		for (Map.Entry<TimeType, Integer> entry : times.entrySet()) {
			int timeAmount = entry.getValue();
			TimeType type = entry.getKey();

		}
	}
}
