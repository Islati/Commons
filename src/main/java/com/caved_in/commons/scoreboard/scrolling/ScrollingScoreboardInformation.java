package com.caved_in.commons.scoreboard.scrolling;

import com.caved_in.commons.Commons;
import com.caved_in.commons.utilities.TextCycler;
import com.google.common.collect.Lists;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents information about a soon-to-be-created scoreboard.
 * This type is intended for returning information that will be used to create a dynamically cycling scoreboard.
 * No length limits exist.
 */
public final class ScrollingScoreboardInformation {
	private static final Commons commons = Commons.getInstance();

	private String prefix = "";

	private String suffix = "";

	private String titleText;
	private int len = 32;
	private TextCycler title; // Lazily initialized
	private List<ScrollingScoreboardEntry> entries;

	public ScrollingScoreboardInformation() {
		entries = new ArrayList<>();
	}

	public ScrollingScoreboardInformation title(String title) {
		titleText = title;
		this.title = new TextCycler(prefix, titleText, len);
		return this;
	}

	public ScrollingScoreboardInformation prefix(String prefix) {
		this.prefix = prefix;
		return this;
	}

	/**
	 * Creates a scoreboard information instance.
	 *
	 * @param title   The title of the scoreboard.
	 * @param entries The entries to include in the scoreboard, in order from top to bottom as they should appear.
	 */
	public ScrollingScoreboardInformation(String title, ScrollingScoreboardEntry... entries) {
		this(null, title, entries);
	}

	/**
	 * Creates a scoreboard information instance.
	 *
	 * @param prefix  The prefix of the title of the scoreboard.
	 * @param title   The title of the scoreboard.
	 * @param entries The entries to include in the scoreboard, in order from top to bottom as they should appear.
	 */
	public ScrollingScoreboardInformation(String prefix, String title, ScrollingScoreboardEntry... entries) {
		this(prefix, title, Lists.newArrayList(entries));
	}

	/**
	 * Creates a scoreboard information instance.
	 *
	 * @param title   The title of the scoreboard.
	 * @param entries The entries to include in the scoreboard, in order from top to bottom as they should appear.
	 */
	public ScrollingScoreboardInformation(String title, List<ScrollingScoreboardEntry> entries) {
		this(null, title, entries);
	}

	/**
	 * Creates a scoreboard information instance.
	 *
	 * @param prefix  The prefix of the title of the scoreboard.
	 * @param title   The title of the scoreboard.
	 * @param entries The entries to include in the scoreboard, in order from top to bottom as they should appear.
	 */
	public ScrollingScoreboardInformation(String prefix, String title, List<ScrollingScoreboardEntry> entries) {
		Validate.notNull(title, "The scoreboard title must not be null.");
		Validate.noNullElements(entries, "Null scoreboard entries are not allowed. Consider using the Spacers class.");
		this.entries = Lists.newArrayList(entries);
		titleText = title;
		this.prefix = prefix;
	}

	/**
	 * Sets the length to which the title cycler will trim text.
	 * <p/>
	 * Due to restrictions of Minecraft, this value must be a natural number less than or equal to 32.
	 *
	 * @param len The new length of the cycler
	 * @throws IllegalStateException If the cycler has already been initialized.
	 */
	public void setCyclerLength(int len) {
		if (title != null) {
			throw new IllegalStateException("The TextCycler instance has already been initialized, and its properties can no longer change.");
		}
		if (len <= 0) {
			throw new IllegalArgumentException("The length must be greater than zero.");
		} else if (len > 32) {
			throw new IllegalArgumentException("The length must not be greater than 32.");
		}
		this.len = len;
	}

	/**
	 * Gets the <b>single cycler reference held by this class</b> which will cycle through the title.
	 *
	 * @return A {@code TextCycler} that cycles through the scoreboard title.
	 */
	public TextCycler getTitle() {
		if (title == null) {
// Lazy initializer allows setting cycler length
// But, now it is time to stop being lazy and actually initialize it
			title = new TextCycler(prefix, titleText, len);
		}
		return title;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setTitle(String title) {
		this.title = new TextCycler(prefix, title, len);
	}

	public void setEntries(ScrollingScoreboardEntry... entries) {
		this.entries.clear();
		Collections.addAll(this.entries, entries);
	}

	public void setEntries(List<ScrollingScoreboardEntry> entries) {
		this.entries.clear();
		this.entries.addAll(entries);

	}

	/**
	 * Gets an unmodifiable collection of existing scoreboard entries. This collection is guaranteed to iterate in the order (from top to bottom) that the entries should appear in to the player.
	 *
	 * @return An unmodifiable collection of scoreboard entries.
	 */
	public List<ScrollingScoreboardEntry> getEntries() {
		return entries;
	}

	@Override
	public int hashCode() {
		final int prime = 997;
		int result = 31;
		result = prime * result
				+ ((entries == null) ? 0 : entries.hashCode());
		result = prime * result
				+ ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ScrollingScoreboardInformation)) {
			return false;
		}
		ScrollingScoreboardInformation other = (ScrollingScoreboardInformation) obj;
		if (entries == null) {
			if (other.entries != null) {
				return false;
			}
		} else if (!entries.equals(other.entries)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ScoreboardInformation [title=" + getTitle()
				+ ", entries=" + getEntries() + "]";
	}
}