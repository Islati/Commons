package com.caved_in.commons.scoreboard;

import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class AbstractScoreboardWrapper implements ScoreboardWrapper {
    private final BoardManager manager;

    private Scoreboard scoreboard;
    private ScoreboardInformation info;

    private DisplaySlot slot;

    public AbstractScoreboardWrapper(@NonNull BoardManager manager, @NonNull Scoreboard board, @NonNull ScoreboardInformation info) {
        this.manager = manager;
        this.scoreboard = board;
        this.info = info;
    }

    @Override
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    @Override
    public void setScoreboard(Scoreboard board) {
        this.scoreboard = board;
    }

    @Override
    public ScoreboardInformation getInfo() {
        return info;
    }

    @Override
    public void setInfo(ScoreboardInformation info) {
        this.info = info;
    }

    @Override
    public void setDisplaySlot(DisplaySlot slot) {
        this.slot = slot;
    }

    @Override
    public BoardManager getManager() {
        return manager;
    }

    @Override
    public ScoreboardWrapper assign(Player p) {
        Objective objective = scoreboard.getObjective(slot);
        objective.setDisplayName(info.getTitle());

        p.setScoreboard(scoreboard);
        return this;
    }
}
