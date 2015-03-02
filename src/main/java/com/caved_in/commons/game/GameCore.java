package com.caved_in.commons.game;

import com.caved_in.commons.game.players.UserManager;

public interface GameCore<T extends UserManager> {

	public void update();

	public long tickDelay();

	public T getUserManager();
}
