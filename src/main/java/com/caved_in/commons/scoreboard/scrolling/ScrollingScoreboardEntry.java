package com.caved_in.commons.scoreboard.scrolling;

import com.caved_in.commons.utilities.TextCycler;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;

/**
 * Represents an entry in a "scoreless" scoreboard.
 * This class can also manipulate text cyclers.
 */
public final class ScrollingScoreboardEntry {
	private String value;
	private String prefix;

	private TextCycler cycler;

	/**
	 * Creates a scoreboard entry with the specified textual value.
	 *
	 * @param value The textual value.
	 */
	public ScrollingScoreboardEntry(String value) {
		this((String) null, value);
	}

	/**
	 * Creates a scoreboard entry with the specified textual value and color code prefix.
	 *
	 * @param prefix The color prefix for the value.
	 * @param value  The textual value.
	 */
	public ScrollingScoreboardEntry(ChatColor prefix, String value) {
		this(new ChatColor[]{prefix}, value);
	}

	/**
	 * Creates a scoreboard entry with the specified textual value and mutliple color code prefix.
	 *
	 * @param prefix The color prefixes for the value, in order.
	 * @param value  The textual value.
	 */
	public ScrollingScoreboardEntry(ChatColor[] prefix, String value) {
		Validate.notNull(value, "A text value must be specified.");
		StringBuilder prefixBuilder = new StringBuilder(prefix == null ? 0 : prefix.length * 2);
		if (prefix != null) {
			for (int i = 0; i < prefix.length; i++) {
				Validate.notNull(prefix[i], "Null prefixes are not allowed.");
				prefixBuilder.append(prefix[i]);
			}
		}
		this.value = value;
		this.prefix = prefixBuilder.toString();
	}

	/**
	 * Creates a scoreboard entry with the specified textual value and prefix.
	 *
	 * @param prefix The textual value prefix, such as a color.
	 * @param value  The textual value.
	 */
	public ScrollingScoreboardEntry(String prefix, String value) {
		Validate.notNull(value, "A text value must be specified.");
		this.value = value;
		this.prefix = prefix == null ? "" : prefix;
	}

	public void setValue(String text) {
		this.value = text;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Get the text of the scoreboard entry.
	 *
	 * @return The untrimmed text of the scoreboard entry, excluding the prefix.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Get the prefix of the scoreboard entry.
	 *
	 * @return The untrimmed prefix of the scoreboard entry, which may be blank if there is no prefix.
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Creates and returns a new text cycler, which cycles through this scoreboard manager entry text value.
	 * <p/>
	 * The returned text cycler will <b>not</b> have a prefix set.
	 * It is the responsibility of the scoreboard displayer to add the prefix, such as through a team.
	 *
	 * @return A new {@link TextCycler} instance, which cycles through scoreboard entry text.
	 */
	public TextCycler createCycler() {
		if (cycler == null || !cycler.getText().equals(getValue()) || (cycler.getPrefix() != null && !cycler.getPrefix().equals(getPrefix()))) {
			cycler = new TextCycler(getPrefix(), getValue(), 16);
		}
		return cycler;
	}

	@Override
	public String toString() {
		return getPrefix() + getValue();
	}

	@Override
	public int hashCode() {
		final int prime = 3119;
		int result = 83;
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		if (!(obj instanceof ScrollingScoreboardEntry)) {
			return false;
		}
		ScrollingScoreboardEntry other = (ScrollingScoreboardEntry) obj;
		if (prefix == null) {
			if (other.prefix != null) {
				return false;
			}
		} else if (!prefix.equals(other.prefix)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}
}