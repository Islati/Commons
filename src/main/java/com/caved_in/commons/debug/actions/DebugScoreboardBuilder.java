package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Commons;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.scoreboard.BoardManager;
import com.caved_in.commons.scoreboard.ScoreboardInformation;
import com.caved_in.commons.scoreboard.ScoreboardType;
import com.caved_in.commons.scoreboard.ScoreboardWrapper;
import org.bukkit.entity.Player;

public class DebugScoreboardBuilder implements DebugAction {
    private static BoardManager manager = Commons.getInstance().getScoreboardManager();
    private ScoreboardWrapper wrapper;

    private boolean inited = false;

    @Override
    public void doAction(Player player, String... args) {
        if (!inited) {
            wrapper = manager.builder().type(ScoreboardType.NORMAL).title("&eTest").dummyObjective()
                    .entry(1, "Line 1?").entry(2, "Line 2?").entry(3, "Line 3?").build();

            manager.assign(player, wrapper);
            manager.getPlugin().debug("Assigned scoreboard wrapper to player" + player.getName());
            inited = true;
            return;
        }

        ScoreboardInformation info = wrapper.getInfo();

        for (int i = 0; i < args.length; i++) {
            info.entry(i + 1, args[i]);
        }

        manager.getPlugin().debug("Updated entries for the scoreboard.");
    }

    @Override
    public String getActionName() {
        return "scoreboard_builder";
    }
}
