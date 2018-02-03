package com.devsteady.onyx.nms;

public interface UnhandledStackTrace extends Thread.UncaughtExceptionHandler {
    @Override
    void uncaughtException(Thread t, Throwable e);

    void register();
}
