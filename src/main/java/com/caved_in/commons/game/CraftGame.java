package com.caved_in.commons.game;

import com.caved_in.commons.game.thread.GameUpdateThread;
import com.caved_in.commons.plugin.BukkitPlugin;

public abstract class CraftGame extends BukkitPlugin implements GameCore {

	private GameUpdateThread updateThread;

	@Override
	public void onEnable() {
		super.onEnable();
		//Create the core update thread and begin
		updateThread = new GameUpdateThread(this);
		getThreadManager().registerSyncRepeatTask("Game Update", updateThread, 0, tickDelay());
	}

	public abstract void startup();

	public abstract void shutdown();

	public abstract String getVersion();

	public abstract String getAuthor();

	public abstract void initConfig();

	@Override
	public abstract void update();

	public abstract long tickDelay();
}
