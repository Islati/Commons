package com.caved_in.commons.world;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 11/01/14
 * Time: 2:32 PM
 */
public enum WorldTime {
	DAWN(0),
	DAY(1000),
	NIGHT(12000);

	private long time;
	WorldTime(long time) {
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public static WorldTime getWorldTime(String time) {
		return WorldTime.valueOf(time.toUpperCase());
	}
}
