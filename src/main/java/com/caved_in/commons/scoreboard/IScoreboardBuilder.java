package com.caved_in.commons.scoreboard;

public interface IScoreboardBuilder {

	public IScoreboardBuilder type(ScoreboardType type);

	public IScoreboardBuilder title(String title);

	public IScoreboardBuilder entry(int score, String text);

	public IScoreboardBuilder entry(ScoreboardEntry entry);

	public IScoreboardBuilder objective(String name, String action);

	public default IScoreboardBuilder dummyObjective() {
		return objective("scoreboard", "dummy");
	}

	public ScoreboardWrapper build();
}
