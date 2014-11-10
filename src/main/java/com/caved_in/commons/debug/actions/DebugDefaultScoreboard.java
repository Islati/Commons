package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Commons;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.scoreboard.BoardManager;
import com.caved_in.commons.scoreboard.ScoreboardInformation;
import com.caved_in.commons.scoreboard.ScoreboardType;
import com.caved_in.commons.scoreboard.ScoreboardWrapper;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.entity.Player;

public class DebugDefaultScoreboard implements DebugAction {
	private static final Commons commons = Commons.getInstance();

	private static BoardManager manager = commons.getScoreboardManager();

	private ScoreboardWrapper wrapper = manager.builder().title("&cDefault board!").dummyObjective().type(ScoreboardType.NORMAL).entry(1, "Line uno").entry(2, "Line deus").build();

	private boolean inited = false;

	@Override
	public void doAction(Player player, String... args) {
		if (!inited) {
			manager.setDefaultScoreboard(wrapper);
			inited = true;
			return;
		}

		ScoreboardInformation info = wrapper.getInfo();
		for (int i = 0; i < args.length; i++) {
			info.entry(i + 1, args[i]);
		}

		commons.debug("Assigned entries " + StringUtil.joinString(args, ", ") + " to default wrapper");
	}

	@Override
	public String getActionName() {
		return "default_scoreboard";
	}
}
