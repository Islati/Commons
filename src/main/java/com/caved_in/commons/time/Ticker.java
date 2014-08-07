package com.caved_in.commons.time;

public interface Ticker {

	public int allowAmount();

	public int getTickCount();

	public void tick();

	public void reset();

	public boolean allow();
}
