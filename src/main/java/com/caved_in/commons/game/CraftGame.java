package com.caved_in.commons.game;

import com.caved_in.commons.game.players.UserManager;
import com.caved_in.commons.game.thread.GameUpdateThread;
import com.caved_in.commons.plugin.BukkitPlugin;

public abstract class CraftGame extends BukkitPlugin implements GameCore {

	@Override
	public void onEnable() {
		super.onEnable();
		//Create the core update thread and begin running it immediately, with the desired delay.
		GameUpdateThread updateThread = new GameUpdateThread(this);
		getThreadManager().registerSyncRepeatTask("Game Update", updateThread, 20	, tickDelay());
	}

	public abstract void startup();

	public abstract void shutdown();

	public abstract String getAuthor();

	public abstract void initConfig();

	@Override
	public abstract void update();

	public abstract long tickDelay();

	public abstract <T extends UserManager> T getUserManager();
}
