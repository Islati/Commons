package com.caved_in.commons.nms.no_implementation;

import com.caved_in.commons.event.StackTraceEvent;
import com.caved_in.commons.nms.UnhandledStackTrace;

public class UnhandledStackTraceNI implements UnhandledStackTrace {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            StackTraceEvent.call(e);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override
    public void register() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}
