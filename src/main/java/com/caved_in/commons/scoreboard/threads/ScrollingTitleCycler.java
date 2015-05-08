package com.caved_in.commons.scoreboard.threads;

import com.caved_in.commons.scoreboard.BoardManager;
import com.caved_in.commons.scoreboard.scrolling.ScrollingScoreboardInformation;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;
import java.util.logging.Level;

public final class ScrollingTitleCycler extends BukkitRunnable {
    private BoardManager manager;
    private UUID playerID;
    private ScrollingScoreboardInformation scoreInfo;


    public ScrollingTitleCycler(BoardManager manager) {
        this.manager = manager;
    }

    public ScrollingTitleCycler(BoardManager manager, UUID id, ScrollingScoreboardInformation info) {
        this.manager = manager;
        this.playerID = id;
        this.scoreInfo = info;
    }

    public void dispose() {
        try {
            cancel();
        } catch (IllegalStateException except) {
            Bukkit.getLogger().log(Level.FINE, "Weird IllegalStateException thrown in TitleCycler. Probably means task isn't running, but is running anyways.", except);
        }
        playerID = null;
        scoreInfo = null;
    }

    @Override
    public void run() {
        if (manager.hasData() || playerID == null || scoreInfo == null || manager.hasData(playerID)) {
            dispose();
            return;
        }

        Scoreboard board = manager.getScoreboard(playerID);
        if (board == null) {
            dispose();
            return;
        }

        Objective objective = board.getObjective(BoardManager.SIDEBOARD_OBJECTIVE_NAME);
        if (objective == null) {
            dispose();
            return;
        }

        String oldTitle = scoreInfo.getTitle().tick();
        String newTitle = scoreInfo.getTitle().toString();

        String objectiveName = objective.getDisplayName();

        //If the title's changed, then we should account for that!
        if ((!objectiveName.equals(oldTitle) && !objectiveName.equals(newTitle)) || (!oldTitle.equals(newTitle))) {
            objective.setDisplayName(newTitle);
        }
    }
}
