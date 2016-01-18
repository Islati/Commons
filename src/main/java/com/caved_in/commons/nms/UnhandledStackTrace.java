package com.caved_in.commons.nms;

public interface UnhandledStackTrace extends Thread.UncaughtExceptionHandler {
    @Override
    void uncaughtException(Thread t, Throwable e);

    void register();
}
