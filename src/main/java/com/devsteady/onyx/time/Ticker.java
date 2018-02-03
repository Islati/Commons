package com.devsteady.onyx.time;

public interface Ticker {

    int allowAmount();

    int getTickCount();

    void tick();

    void reset();

    boolean allow();
}
