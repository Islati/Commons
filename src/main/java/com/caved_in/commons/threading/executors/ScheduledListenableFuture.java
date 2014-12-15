package com.caved_in.commons.threading.executors;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableScheduledFuture;

import java.util.concurrent.*;

public interface ScheduledListenableFuture<V>
        extends RunnableScheduledFuture<V>, ListenableFuture<V>, ListenableScheduledFuture<V> {
    @Override
    void addListener(Runnable runnable, Executor executor);

    @Override
    boolean isPeriodic();

    @Override
    long getDelay(TimeUnit unit);

    @Override
    int compareTo(Delayed o);

    @Override
    void run();

    @Override
    boolean cancel(boolean mayInterruptIfRunning);

    @Override
    boolean isCancelled();

    @Override
    boolean isDone();

    @Override
    V get() throws InterruptedException, ExecutionException;

    @Override
    V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;
}