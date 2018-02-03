package com.devsteady.onyx.nms.no_implementation;

import com.devsteady.onyx.event.StackTraceEvent;
import com.devsteady.onyx.nms.UnhandledStackTrace;

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
