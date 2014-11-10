package com.caved_in.commons.scoreboard;

import com.caved_in.commons.scoreboard.scrolling.ScrollingScoreboardEntry;
import org.bukkit.ChatColor;

/**
 * A class containing entries intended for use as scoreboard spacers.
 * There are multiple values because scoreboard entries must be unique.
 *
 * @deprecated This class will be replaced with a more vertasile system of dealing with duplicate values, rendering it pointless as {@link ScorelessBoardManager#SPACER_ENTRY} can be used.
 */
@Deprecated
public final class Spacers {
	private Spacers() {
	}

	/**
	 * A string representing 16 spaces. Intended for use as the first spacer.
	 *
	 * @deprecated Use {@link ScorelessBoardManager#SPACER_ENTRY}.
	 */
	@Deprecated
	public static final String FIRST = " ";
	/**
	 * A string representing a reset color code and 14 spaces. Intended for use as the second spacer.
	 *
	 * @deprecated Will not be used, being replaced with a more vertasile system of dealing with duplicate values.
	 */
	@Deprecated
	public static final String SECOND = ChatColor.RESET.toString() + " ";
	/**
	 * A string representing two reset color codes and 12 spaces. Intended for use as the third spacer.
	 *
	 * @deprecated Will not be used, being replaced with a more vertasile system of dealing with duplicate values.
	 */
	@Deprecated
	public static final String THIRD = ChatColor.RESET.toString() + ChatColor.RESET.toString() + " ";
	/**
	 * A string representing three reset color codes and 10 spaces. Intended for use as the fourth spacer.
	 *
	 * @deprecated Will not be used, being replaced with a more vertasile system of dealing with duplicate values.
	 */
	@Deprecated
	public static final String FOURTH = ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + " ";
	/**
	 * A string representing four reset color codes and 8 spaces. Intended for use as the fifth spacer.
	 *
	 * @deprecated Will not be used, being replaced with a more vertasile system of dealing with duplicate values.
	 */
	@Deprecated
	public static final String FIFTH = ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + " ";
	/**
	 * A string representing five reset color codes and 6 spaces. Intended for use as the sixth spacer.
	 *
	 * @deprecated Will not be used, being replaced with a more vertasile system of dealing with duplicate values.
	 */
	@Deprecated
	public static final String SIXTH = ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + " ";
	/**
	 * A string representing 6 reset color codes and four spaces. Intended for use as the seventh spacer.
	 *
	 * @deprecated Will not be used, being replaced with a more vertasile system of dealing with duplicate values.
	 */
	@Deprecated
	public static final String SEVENTH = ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + " ";
	/**
	 * A string representing 7 reset color codes and two spaces. Intended for use as the eighth spacer.
	 *
	 * @deprecated Will not be used, being replaced with a more vertasile system of dealing with duplicate values.
	 */
	@Deprecated
	public static final String EIGHTH = ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString() + " ";

	private static final String[] SPACERS = new String[]{
			FIRST,
			SECOND,
			THIRD,
			FOURTH,
			FIFTH,
			SIXTH,
			SEVENTH,
			EIGHTH,
	};

	/**
	 * Gets the spacer with the specified number.
	 *
	 * @param number The number representing the occurence index of the spacer, base one (a value of {@code 1} will return the first spacer).
	 * @return A spacer value, or {@code null} if not found for that number.
	 * @deprecated Uses deprecated spacer fields, and is obselete due to the upcoming vertasile system of dealing with duplicate values.
	 */
	@Deprecated
	public static String getSpacer(int number) {
		if (number < 1 || number >= 8) {
			return null;
		}

		return SPACERS[number - 1];
	}

	/**
	 * Create a scoreboard entry for the given spacer.
	 *
	 * @param spacer The spacer string.
	 * @return A scoreboard entry representing that spacer.
	 * @deprecated Will not be needed, use {@link com.caved_in.commons.scoreboard.scrolling.ScrollingScoreboardEntry#ScrollingScoreboardEntry(String)} with {@link ScorelessBoardManager#SPACER_ENTRY}.
	 */
	@Deprecated
	public static ScrollingScoreboardEntry createEntry(String spacer) {
		return new ScrollingScoreboardEntry(spacer);
	}
}