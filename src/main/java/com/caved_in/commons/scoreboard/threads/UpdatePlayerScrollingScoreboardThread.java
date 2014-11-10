package com.caved_in.commons.scoreboard.threads;

import com.caved_in.commons.Commons;
import com.caved_in.commons.scoreboard.ScoreboardEntry;
import com.caved_in.commons.scoreboard.scrolling.ScrollingScoreboardEntry;
import com.caved_in.commons.scoreboard.scrolling.ScrollingScoreboardWrapper;
import com.caved_in.commons.utilities.TextCycler;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UpdatePlayerScrollingScoreboardThread extends BukkitRunnable {
	private boolean populated;

	private static final Commons commons = Commons.getInstance();

	private Map<ScrollingScoreboardEntry, IndexedTextCycler> indexedEntryCyclers = new HashMap<>();
	private Map<String, Team> prefixesToTeams = new HashMap<>();

	private ScrollingScoreboardWrapper wrapper;

	public UpdatePlayerScrollingScoreboardThread(ScrollingScoreboardWrapper wrapper) {
		this.wrapper = wrapper;
	}

	private void init() {
		indexedEntryCyclers.clear();
		prefixesToTeams.clear();

		Collection<ScoreboardEntry> entries = wrapper.getInfo().getEntries();

		int entryCount = entries.size();

		commons.debug("Entries count = " + entryCount);

		Scoreboard scoreboard = wrapper.getScoreboard();
//
//
//
//		for(int i = entryCount - 1; i >= 0; i--){
//			ScrollingScoreboardEntry entry = entries.get(i);
//
//			entry.setPrefix(String.valueOf(i));
//
//			String val = entry.getValue();
//			if (val == null || val.isEmpty() || val.equalsIgnoreCase("")) {
//				commons.debug("Value @ Entry " + i + " of " + entries.toString() + " is null or empty..");
//				continue;
//			}
//
//			if (entry == null) {
//				commons.debug("Entry @ " + i + " = null 0.0");
//				continue;
//			}
//
//			IndexedTextCycler cycler = new IndexedTextCycler(entryCount - i,entry.createCycler());
//
//			indexedEntryCyclers.put(entry,cycler);
//
//			String entryPrefix = entry.getPrefix();
//
//			if (prefixesToTeams.containsKey(entryPrefix)) {
//				continue;
//			}
//
//			String teamName = StringUtils.trimToEmpty(entryPrefix.replace(ChatColor.COLOR_CHAR, '&').replace(' ', '-'));
//			if(teamName.length() > 16) {
//				teamName = teamName.substring(0, 16);
//			}
//
//			if (scoreboard.getTeam(teamName) != null) {
//				commons.debug("Team " + teamName + " already exists!");
//				continue;
//			}
//
//			Team value = scoreboard.registerNewTeam(teamName);
//			value.setPrefix(entryPrefix);
//			prefixesToTeams.put(entryPrefix, value);
//
//			commons.debug("Registering team : " + teamName + " @ Element [" + i + "]");
//		}
	}

	@Override
	public void run() {
//		List<ScrollingScoreboardEntry> entries = wrapper.getInfo().getEntries();
//
//		Scoreboard scoreboard = wrapper.getScoreboard();
//
//		Objective obj = scoreboard.getObjective(DisplaySlot.SIDEBAR);
//
//		for(ScrollingScoreboardEntry e : entries){
//			IndexedTextCycler indexedCycler = indexedEntryCyclers.get(e);
//
//			if (indexedCycler == null) {
//				commons.debug("There's no available cycler for entry " + e.toString());
//				init();
//				return;
//			}
//
//			TextCycler cycler = indexedCycler.getCycler();
//
//			String oldVal = cycler.tick();
//			String newVal = cycler.toString();
//
//			if (oldVal == newVal && populated) {
//				continue;
//			}
//
//			scoreboard.resetScores(oldVal);
////					Team team = prefixesToTeams.get(e.getPrefix());
////					team.removePlayer(Players.getOfflinePlayer(oldVal));
////					team.addPlayer(Players.getOfflinePlayer(newVal));
//			obj.getScore(newVal).setScore(indexedCycler.getIndex());
//			populated = true;
//		}
	}

	public static class IndexedTextCycler {
		private TextCycler cycler;
		private int index;

		public IndexedTextCycler(int index, TextCycler cycler) {
			this.index = index;
			this.cycler = cycler;
		}

		public int getIndex() {
			return index;
		}

		public TextCycler getCycler() {
			return cycler;
		}

		public void setCycler(TextCycler cycler) {
			this.cycler = cycler;
		}
	}
}
