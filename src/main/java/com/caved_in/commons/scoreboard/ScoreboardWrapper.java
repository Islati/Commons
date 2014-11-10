package com.caved_in.commons.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collection;

public interface ScoreboardWrapper {

	public Scoreboard getScoreboard();

	public void setScoreboard(Scoreboard board);

	public ScoreboardInformation getInfo();

	public void setInfo(ScoreboardInformation info);

	public void setDisplaySlot(DisplaySlot slot);

	public BoardManager getManager();

	public ScoreboardWrapper assign(Player p);

	public default boolean hasThreads() {
		return false;
	}

	public default Collection<? extends Runnable> getThreads() {
		return null;
	}
}
