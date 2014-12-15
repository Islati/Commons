package com.caved_in.commons.scoreboard;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class IndexedScoreboardEntry extends AbstractScoreboardEntry {
	private static final Map<Integer, Integer> positions = ImmutableMap.<Integer, Integer>builder()
			.put(0, 16).put(1, 15).put(2, 14).put(3, 13).put(4, 12).put(5, 11)
			.put(6, 10).put(7, 9).put(8, 8).put(9, 7).put(10, 6).put(11, 5).put(12, 4)
			.put(13, 3).put(14, 2).put(15, 1).put(16, 0).build();

	public static int getScoreAtSlot(int score) {
		if (!positions.containsKey(score)) {
			if (score < 0) {
				return 16;
			}

			if (score > 16) {
				return 0;
			}
		}

		return positions.get(score);
	}


	private int position;

	public IndexedScoreboardEntry() {

	}

	public IndexedScoreboardEntry position(int position) {
		setScore(position);
		return this;
	}

	public IndexedScoreboardEntry text(String text) {
		setValue(text);
		return this;
	}

	@Override
	public int getScore() {
		return position;
	}

	@Override
	public void setScore(int score) {
		if (!positions.containsKey(score)) {
			if (score < 0) {
				position = 16;
			}

			if (score > 16) {
				position = 0;
			}
		}

		this.position = positions.get(score);
	}
}
