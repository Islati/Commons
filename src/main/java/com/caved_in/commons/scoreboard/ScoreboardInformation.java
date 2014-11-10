package com.caved_in.commons.scoreboard;

import java.util.Collection;

public interface ScoreboardInformation {

	public ScoreboardInformation title(String text);

	public ScoreboardInformation entry(int score, String text);

	public String getTitle();

	public void setEntries(Collection<ScoreboardEntry> entries);

	public void setEntries(ScoreboardEntry... entries);

	public Collection<ScoreboardEntry> getEntries();

	public ScoreboardEntry getEntry(int index);
}
