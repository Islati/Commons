package com.caved_in.commons.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Scoreboards {
    public static final ScoreboardManager MANAGER = Bukkit.getScoreboardManager();

    public static Scoreboard main() {
        return MANAGER.getMainScoreboard();
    }

    public static Scoreboard make() {
        return MANAGER.getNewScoreboard();
    }

    public static Objective register(Scoreboard board, String objective) {
        return register(board, objective, "dummy");
    }

    public static Objective register(Scoreboard board, String objective, String action) {
        return board.registerNewObjective(objective, action);
    }


}
