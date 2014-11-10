package com.caved_in.commons.scoreboard;

import com.caved_in.commons.utilities.StringUtil;
import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class BasicScoreboardInformation implements ScoreboardInformation {
	private String title;

	private Map<Integer, ScoreboardEntry> indexedEntries = new HashMap<>();

	@Override
	public ScoreboardInformation title(@NonNull String text) {
		this.title = StringUtil.colorize(text);
		return this;
	}

	public ScoreboardInformation entry(@NonNull int position, @NonNull String text) {
		int correspondingSlot = IndexedScoreboardEntry.getScoreAtSlot(position);

		if (!indexedEntries.containsKey(correspondingSlot)) {
			indexedEntries.put(correspondingSlot, new IndexedScoreboardEntry().position(position).text(StringUtil.colorize(text)));
			return this;
		}

		indexedEntries.get(correspondingSlot).setValue(text);
		return this;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setEntries(Collection<ScoreboardEntry> entries) {
		for (ScoreboardEntry entry : entries) {
			int score = entry.getScore();
			//If theres already an index for the specified score,
			if (indexedEntries.containsKey(score)) {
				indexedEntries.get(score).setValue(entry.getValue());
			} else {
				this.indexedEntries.put(entry.getScore(), entry);
			}
		}
	}

	@Override
	public void setEntries(ScoreboardEntry... entries) {
		setEntries(Sets.newHashSet(entries));
	}

	@Override
	public Collection<ScoreboardEntry> getEntries() {
		return indexedEntries.values();
	}

	@Override
	public ScoreboardEntry getEntry(int index) {
		int correspondingSlot = IndexedScoreboardEntry.getScoreAtSlot(index);
		return indexedEntries.get(correspondingSlot);
	}
}
