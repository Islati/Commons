package com.caved_in.commons.threading.executors;

import com.caved_in.commons.threading.executors.AbstractListeningService.RunnableAbstractFuture;
import com.google.common.base.Preconditions;

import java.util.concurrent.*;

class CallableTask<T> extends RunnableAbstractFuture<T> {
    protected final Callable<T> compute;

    public CallableTask(Callable<T> compute) {
        Preconditions.checkNotNull(compute, "compute cannot be NULL");

        this.compute = compute;
    }

    public ScheduledListenableFuture<T> getScheduledFuture(final long startTime, final long nextDelay) {
        return new ScheduledListenableFuture<T>() {

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return CallableTask.this.cancel(mayInterruptIfRunning);
            }

            @Override
            public T get() throws InterruptedException, ExecutionException {
                return CallableTask.this.get();
            }

            @Override
            public T get(long timeout, TimeUnit unit) throws InterruptedException,
                    ExecutionException, TimeoutException {
                return CallableTask.this.get(timeout, unit);
            }

            @Override
            public boolean isCancelled() {
                return CallableTask.this.isCancelled();
            }

            @Override
            public boolean isDone() {
                return CallableTask.this.isDone();
            }

            @Override
            public void addListener(Runnable listener, Executor executor) {
                CallableTask.this.addListener(listener, executor);
            }

            @Override
            public int compareTo(Delayed o) {
                return Long.compare(
                        getDelay(TimeUnit.NANOSECONDS),
                        o.getDelay(TimeUnit.NANOSECONDS)
                );
            }

            @Override
            public long getDelay(TimeUnit unit) {
                long current = System.nanoTime();

                // Calculate the correct canUse
                if (current < startTime || !isPeriodic()) {
                    return unit.convert(startTime - current, TimeUnit.NANOSECONDS);
                } else {
                    return unit.convert(((current - startTime) % nextDelay), TimeUnit.NANOSECONDS);
                }
            }

            @Override
            public boolean isPeriodic() {
                return nextDelay > 0;
            }

            @Override
            public void run() {
                compute();
            }
        };
    }

    /**
     * Invoked by the thread responsible for computing this future.
     */
    protected void compute() {
        try {
            // Save result
            if (!isCancelled()) {
                set(compute.call());
            }
        } catch (Throwable e) {
            setException(e);
        }
    }

    @Override
    public void run() {
        compute();
    }
}