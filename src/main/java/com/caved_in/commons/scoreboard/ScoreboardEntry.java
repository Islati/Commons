package com.caved_in.commons.scoreboard;

public interface ScoreboardEntry {

    public String getValue();

    public String getPreviousValue();

    public int getScore();

    public int getPreviousScore();

    public void setValue(String text);

    public void setScore(int score);

    public void setChanged(boolean changed);

    public boolean hasChanged();
}
