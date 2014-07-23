package com.caved_in.commons.plugin.game;

import org.bukkit.event.Listener;

public interface GameState extends Listener {
	public void update();

	public int id();

	public boolean switchState();

	public int nextState();
}
