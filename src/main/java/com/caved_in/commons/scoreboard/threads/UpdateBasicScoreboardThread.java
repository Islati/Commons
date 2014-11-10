package com.caved_in.commons.scoreboard.threads;

import com.caved_in.commons.scoreboard.ScoreboardEntry;
import com.caved_in.commons.scoreboard.ScoreboardWrapper;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collection;

public class UpdateBasicScoreboardThread extends BukkitRunnable {
	private ScoreboardWrapper wrapper;

	private boolean populated;

	public UpdateBasicScoreboardThread(ScoreboardWrapper wrapper) {
		this.wrapper = wrapper;
	}

	@Override
	public void run() {
		Scoreboard scoreboard = wrapper.getScoreboard();

		Collection<ScoreboardEntry> entries = wrapper.getInfo().getEntries();

		Objective obj = scoreboard.getObjective(DisplaySlot.SIDEBAR);

		//If the board's not populated then loop through every
		//entry
		if (!populated) {
			entries.forEach(e -> {
				obj.getScore(e.getValue()).setScore(e.getScore());
			});
			populated = true;
		}

		for (ScoreboardEntry entry : entries) {

			//If the scoreboard has already been populated, and nothings changed then continue.
			if (!entry.hasChanged()) {
				continue;
			}

			//Reset the previous score, as if it's changed we don't want it anymore!
			String oldText = entry.getPreviousValue();
			scoreboard.resetScores(oldText);

			//And finally, initialize a score of the new value and mark the entry as un-changed,
			//so it doesn't loop again and such.
			obj.getScore(entry.getValue()).setScore(entry.getScore());
			entry.setChanged(false);
		}

	}
}
