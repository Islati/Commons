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

	public void registerSynchRepeatTask(String name, Runnable task, long delayInTicks, long repeatTimeInTicks) {
		if (!runningTasks.containsKey(name)) {
			runningTasks.put(name, plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, task, delayInTicks, repeatTimeInTicks));
		}
	}

	public void registerASynchRepeatTask(String name, Runnable task, long delayInTicks, long repeatTimeInTicks) {
		if (!runningTasks.containsKey(name)) {
			runningTasks.put(name, plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, task, delayInTicks, repeatTimeInTicks));
		}
	}

	public void runTaskNow(Runnable task) {
		plugin.getServer().getScheduler().runTask(this.plugin, task);
	}

	public void runTaskAsynch(Runnable task) {
		plugin.getServer().getScheduler().runTaskAsynchronously(plugin, task);
	}

	public void runTaskLater(Runnable task, long delayInTicks) {
		plugin.getServer().getScheduler().runTaskLater(plugin, task, delayInTicks);
	}

	public void runTaskOneTickLater(Runnable task) {
		plugin.getServer().getScheduler().runTaskLater(plugin, task, 1);
	}

	public void runTaskLaterAsynch(Runnable task, long delay) {
		plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, task, delay);
	}

	public boolean cancelTask(String name) {
		if (runningTasks.containsKey(name)) {
			Bukkit.getScheduler().cancelTask(runningTasks.get(name));
			runningTasks.remove(name);
			return true;
		}
		return false;
	}

}
