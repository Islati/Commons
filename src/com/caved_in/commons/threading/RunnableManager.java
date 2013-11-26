package com.caved_in.commons.threading;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class RunnableManager {
	private JavaPlugin plugin;
	private HashMap<String, Integer> runningTasks = new HashMap<String, Integer>();

	public RunnableManager(JavaPlugin Plugin) {
		this.plugin = Plugin;
	}

	public void registerSynchRepeatTask(String Name, Runnable Task, long DelayInTicks, long RepeatTimeInTicks) {
		if (!runningTasks.containsKey(Name)) {
			runningTasks.put(Name, this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, Task, DelayInTicks, RepeatTimeInTicks));
		}
	}

	public void registerASynchRepeatTask(String Name, Runnable Task, long DelayInTicks, long RepeatTimeInTicks) {
		if (!runningTasks.containsKey(Name)) {
			runningTasks.put(Name, this.plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(this.plugin, Task, DelayInTicks, RepeatTimeInTicks));
		}
	}

	public void runTaskNow(Runnable Task) {
		this.plugin.getServer().getScheduler().runTask(this.plugin, Task);
	}

	public void runTaskAsynch(Runnable Task) {
		this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, Task);
	}

	public void runTaskLater(Runnable Task, long DelayInTicks) {
		this.plugin.getServer().getScheduler().runTaskLater(this.plugin, Task, DelayInTicks);
	}

	public void runTaskOneTickLater(Runnable task) {
		this.plugin.getServer().getScheduler().runTaskLater(this.plugin, task, 1);
	}

	public void runTaskLaterAsynch(Runnable Task, long Delay) {
		this.plugin.getServer().getScheduler().runTaskLaterAsynchronously(this.plugin, Task, Delay);
	}

	public boolean cancelTask(String Name) {
		if (this.runningTasks.containsKey(Name)) {
			Bukkit.getScheduler().cancelTask(this.runningTasks.get(Name));
			this.runningTasks.remove(Name);
			return true;
		}
		return false;
	}

}
