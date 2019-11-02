/* 
 * Logged-classes - Simple proxy-classes for adding better error handling in Minecraft.
 * Copyright (C) 2012 Kristian S. Stangeland
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.

 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 */

package com.caved_in.commons.plugin;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Implements a delegating scheduler that automatically handles all exceptions.
 *
 * @author Kristian
 */
public abstract class LoggedScheduler implements BukkitScheduler {

    private class TaskedRunnable implements Runnable {

        private int taskID = -1;
        private Runnable delegate;

        public TaskedRunnable(Runnable delegate) {
            this.delegate = delegate;
        }

        @Override
        public void run() {
            try {
                delegate.run();
            } catch (Throwable e) {
                customHandler(taskID, e);
            }
        }

        public int getTaskID() {
            return taskID;
        }

        public void setTaskID(int taskID) {
            this.taskID = taskID;
        }
    }

    // A reference to the underlying scheduler
    private BukkitScheduler delegate;

    public LoggedScheduler(Plugin owner) {
        this(owner.getServer().getScheduler());
    }

    public LoggedScheduler(BukkitScheduler delegate) {
        this.delegate = delegate;
    }

    /**
     * Invoked when an error occurs in a task.
     *
     * @param taskID - unique ID of the task, or
     * @param e      - error that occured.
     */
    protected abstract void customHandler(int taskID, Throwable e);


    @Override
    public BukkitTask runTask(Plugin plugin, Runnable runnable) throws IllegalArgumentException {
        TaskedRunnable thread = new TaskedRunnable(runnable);
        return delegate.runTask(plugin, thread);
    }

    @Override
    public BukkitTask runTask(Plugin plugin, BukkitRunnable bukkitRunnable) throws IllegalArgumentException {
        TaskedRunnable thread = new TaskedRunnable(bukkitRunnable);
        return delegate.runTask(plugin, thread);
    }

    @Override
    public BukkitTask runTaskAsynchronously(Plugin plugin, Runnable runnable) throws IllegalArgumentException {
        TaskedRunnable thread = new TaskedRunnable(runnable);
        return delegate.runTaskAsynchronously(plugin, thread);
    }

    @Override
    public BukkitTask runTaskAsynchronously(Plugin plugin, BukkitRunnable bukkitRunnable) throws IllegalArgumentException {
        TaskedRunnable thread = new TaskedRunnable(bukkitRunnable);
        return delegate.runTaskAsynchronously(plugin, thread);
    }

    @Override
    public BukkitTask runTaskLater(Plugin plugin, Runnable runnable, long delayInTicks) throws IllegalArgumentException {
        TaskedRunnable thread = new TaskedRunnable(runnable);
        return delegate.runTaskLater(plugin, thread, delayInTicks);
    }

    @Override
    public BukkitTask runTaskLater(Plugin plugin, BukkitRunnable bukkitRunnable, long l) throws IllegalArgumentException {
        TaskedRunnable thread = new TaskedRunnable(bukkitRunnable);
        return delegate.runTaskLater(plugin, thread, l);
    }

    @Override
    public BukkitTask runTaskLaterAsynchronously(Plugin plugin, Runnable runnable, long l) throws IllegalArgumentException {
        TaskedRunnable thread = new TaskedRunnable(runnable);
        return delegate.runTaskLaterAsynchronously(plugin, thread, l);
    }

    @Override
    public BukkitTask runTaskLaterAsynchronously(Plugin plugin, BukkitRunnable bukkitRunnable, long l) throws IllegalArgumentException {
        TaskedRunnable thread = new TaskedRunnable(bukkitRunnable);
        return delegate.runTaskLaterAsynchronously(plugin, thread, l);
    }

    @Override
    public BukkitTask runTaskTimer(Plugin plugin, Runnable runnable, long delayInTicks, long repeatTicks) throws IllegalArgumentException {
        TaskedRunnable thread = new TaskedRunnable(runnable);
        return delegate.runTaskTimer(plugin, thread, delayInTicks, repeatTicks);
    }

    @Override
    public BukkitTask runTaskTimer(Plugin plugin, BukkitRunnable bukkitRunnable, long l, long l1) throws IllegalArgumentException {
        TaskedRunnable thread = new TaskedRunnable(bukkitRunnable);
        return delegate.runTaskTimer(plugin, thread, l, l1);
    }

    @Override
    public BukkitTask runTaskTimerAsynchronously(Plugin plugin, Runnable runnable, long delay, long repeat) throws IllegalArgumentException {
        TaskedRunnable thread = new TaskedRunnable(runnable);
        return delegate.runTaskTimerAsynchronously(plugin, thread, delay, repeat);
    }

    @Override
    public BukkitTask runTaskTimerAsynchronously(Plugin plugin, BukkitRunnable bukkitRunnable, long l, long l1) throws IllegalArgumentException {
        TaskedRunnable thread = new TaskedRunnable(bukkitRunnable);
        return delegate.runTaskTimerAsynchronously(plugin, thread, l, l1);
    }

    @Override
    public <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task) {
        return delegate.callSyncMethod(plugin, task);
    }

    public void cancelAllTasks(Plugin plugin) {
        delegate.cancelTasks(plugin);
    }

    @Override
    public void cancelTask(int taskId) {
        delegate.cancelTask(taskId);
    }

    @Override
    public void cancelTasks(Plugin plugin) {
        delegate.cancelTasks(plugin);
    }

    @Override
    public List<BukkitWorker> getActiveWorkers() {
        return delegate.getActiveWorkers();
    }

    @Override
    public List<BukkitTask> getPendingTasks() {
        return delegate.getPendingTasks();
    }

    @Override
    public boolean isCurrentlyRunning(int taskId) {
        return delegate.isCurrentlyRunning(taskId);
    }

    @Override
    public boolean isQueued(int taskId) {
        return delegate.isQueued(taskId);
    }

    @Override
    public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task) {
        TaskedRunnable wrapped = new TaskedRunnable(task);

        wrapped.setTaskID(delegate.scheduleAsyncDelayedTask(plugin, wrapped));
        return wrapped.getTaskID();
    }

    @Override
    public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task, long delay) {
        TaskedRunnable wrapped = new TaskedRunnable(task);

        wrapped.setTaskID(delegate.scheduleAsyncDelayedTask(plugin, wrapped, delay));
        return wrapped.getTaskID();
    }

    @Override
    public int scheduleAsyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
        TaskedRunnable wrapped = new TaskedRunnable(task);

        wrapped.setTaskID(delegate.scheduleAsyncRepeatingTask(plugin, wrapped, delay, period));
        return wrapped.getTaskID();
    }

    @Override
    public int scheduleSyncDelayedTask(Plugin plugin, Runnable task) {
        TaskedRunnable wrapped = new TaskedRunnable(task);

        wrapped.setTaskID(delegate.scheduleSyncDelayedTask(plugin, wrapped));
        return wrapped.getTaskID();
    }

    @Override
    public int scheduleSyncDelayedTask(Plugin plugin, BukkitRunnable bukkitRunnable) {
        TaskedRunnable wrapped = new TaskedRunnable(bukkitRunnable);
        wrapped.setTaskID(delegate.scheduleSyncDelayedTask(plugin, wrapped));
        return wrapped.getTaskID();
    }

    @Override
    public int scheduleSyncDelayedTask(Plugin plugin, Runnable task, long delay) {
        TaskedRunnable wrapped = new TaskedRunnable(task);

        wrapped.setTaskID(delegate.scheduleSyncDelayedTask(plugin, wrapped, delay));
        return wrapped.getTaskID();
    }

    @Override
    public int scheduleSyncDelayedTask(Plugin plugin, BukkitRunnable bukkitRunnable, long l) {
        TaskedRunnable wrapped = new TaskedRunnable(bukkitRunnable);
        wrapped.setTaskID(delegate.scheduleSyncDelayedTask(plugin, bukkitRunnable, l));

        return wrapped.getTaskID();
    }

    @Override
    public int scheduleSyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
        TaskedRunnable wrapped = new TaskedRunnable(task);

        wrapped.setTaskID(delegate.scheduleSyncRepeatingTask(plugin, wrapped, delay, period));
        return wrapped.getTaskID();
    }

    @Override
    public int scheduleSyncRepeatingTask(Plugin plugin, BukkitRunnable bukkitRunnable, long l, long l1) {
        TaskedRunnable wrapped = new TaskedRunnable(bukkitRunnable);
        wrapped.setTaskID(delegate.scheduleSyncRepeatingTask(plugin, wrapped, l, l1));
        return wrapped.getTaskID();
    }
}