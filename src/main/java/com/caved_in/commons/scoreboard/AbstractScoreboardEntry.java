package com.caved_in.commons.scoreboard;

import static com.caved_in.commons.utilities.StringUtil.colorize;

public abstract class AbstractScoreboardEntry implements ScoreboardEntry {
    private String oldText = "";
    private String text = "";

    private int oldScore = 0;
    private int score = 0;

    private boolean change = false;

    public AbstractScoreboardEntry(String text, int score) {
        this.text = colorize(text);
        this.score = score;
    }

    public AbstractScoreboardEntry() {

    }

    @Override
    public String getValue() {
        return text;
    }

    @Override
    public String getPreviousValue() {
        return oldText;
    }

    public void setValue(String text) {
        this.oldText = this.text;
        this.text = colorize(text);
        change = true;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getPreviousScore() {
        return oldScore;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
        change = true;
    }

    @Override
    public boolean hasChanged() {
        return change;
    }

    @Override
    public void setChanged(boolean changed) {
        this.change = changed;
    }
}
