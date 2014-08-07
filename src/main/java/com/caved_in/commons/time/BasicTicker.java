package com.caved_in.commons.time;

public class BasicTicker implements Ticker {

	private int amount = 0;

	private int ticks = 0;

	public BasicTicker(int allowAmount) {
		this.amount = allowAmount;
	}

	@Override
	public int allowAmount() {
		return amount;
	}

	@Override
	public int getTickCount() {
		return ticks;
	}

	@Override
	public void tick() {
		ticks++;
	}

	@Override
	public void reset() {
		ticks = 0;
	}

	@Override
	public boolean allow() {
		if (getTickCount() >= allowAmount()) {
			reset();
			return true;
		}
		return false;
	}
}
