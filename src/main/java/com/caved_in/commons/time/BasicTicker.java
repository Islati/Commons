package com.caved_in.commons.time;

public class BasicTicker implements Ticker {

    private int amount = 0;

    private int ticks = 0;

    /*
    Whether or not the ticker will reset after its allow methods passed.
     */
    private boolean resetAfterAllow = true;

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
            if (resetAfterAllow()) {
                reset();
            }
            return true;
        }
        return false;
    }

    public boolean resetAfterAllow() {
        return resetAfterAllow;
    }

    public void setResetAfterAllow(boolean val) {
        this.resetAfterAllow = val;
    }
}
