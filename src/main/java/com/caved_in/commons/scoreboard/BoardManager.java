package com.caved_in.commons.scoreboard;

import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.BukkitPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

public interface BoardManager {
    /**
     * The name of the sideboard objective which is displayed to the player.
     */
    public static final String SIDEBOARD_OBJECTIVE_NAME = "scoreboard";

    public default ScoreboardWrapper getDefaultWrapper() {
        return null;
    }

    /**
     * @return
     */
    public default boolean hasDefaultScoreboard() {
        return false;
    }

    /**
     * Create and set a default scoreboard for the board manager.
     * <p>
     * This is optional, as sometimes it's best to have one scoreboard, as opposed to a load of them.
     *
     * @param wrapper
     */
    public default void setDefaultScoreboard(ScoreboardWrapper wrapper) {

    }

    /**
     * Whether or not the board-manager has any data cached.
     *
     * @return true if there's any data loaded into the board waters; false otherwise.
     */
    public boolean hasData();

    /**
     * Whether or not the player has any cached data.
     *
     * @param p player to check if any cached data exists.
     * @return true if the player has any data cached.
     */
    public default boolean hasData(Player p) {
        return hasData(p.getUniqueId());
    }

    public boolean hasData(UUID id);

    /**
     * Retrieve the scoreboard wrapper for the given player.
     *
     * @param id uuid of the player to retrieve the wrapped data for.
     * @return scoreboard wrapper for the given player.
     */
    public ScoreboardWrapper getWrapper(UUID id);

    public default ScoreboardWrapper getWrapper(Player p) {
        return getWrapper(p.getUniqueId());
    }

    /**
     * Get the scoreboard information from cache that populates the players scoreboard.
     * <p>
     * Modifying the scoreboard information that's returned will reflect changes
     * to the players scoreboard on the next available update.
     *
     * @param p player to get the scoreboard information for.
     * @return scoreboard information that populated the players scoreboard.
     */
    public default ScoreboardInformation getData(Player p) {
        return getData(p.getUniqueId());
    }

    public ScoreboardInformation getData(UUID id);

    /**
     * Retrieve the cached scoreboard for the player.
     *
     * @param p player to get the cached scoreboard for.
     * @return the scoreboard for the player if it exists in cache, otherwise null.
     */
    public default Scoreboard getScoreboard(Player p) {
        return getScoreboard(p.getUniqueId());
    }

    public Scoreboard getScoreboard(UUID id);

    /**
     * Reset the players scoreboard to the main scoreboard; If there is no main scoreboard
     * then the players scoreboard will be cleared.
     *
     * @param p player to reset the scoreboard for.
     */
    public void resetScoreboard(Player p);

    public default void resetScoreboard(UUID id) {
        resetScoreboard(Players.getPlayer(id));
    }

    /**
     * @return parent plugin for this board manager.
     */
    public BukkitPlugin getPlugin();

    /**
     * Create a new scoreboard builder, used in customizing and creating scoreboards for players.
     *
     * @return a new scoreboard builder instance.
     */
    public ScoreboardBuilder builder();

    /**
     * Set the players scoreboard to the given scoreboard wrapper.
     * <p>
     * All information in the scoreboard wrapper populates the players scoreboard with entries,
     * enables the scrolling text features, and handles interactions with the wrapped scoreboard instance.
     *
     * @param player  player to assign the scoreboard to.
     * @param wrapper wrapper to assign to the player
     */
    public void assign(Player player, ScoreboardWrapper wrapper);

    /**
     * Bake elements of the scoreboard into a nice tasty wrapper with all the objectives registered,
     * title set, and elements displayed.
     * <p>
     * The wrapper contains a scoreboard, and the scoreboard information used to create the scoreboard and render elements.
     * The Objective register data is used as an alternative, syntactic sugar approach for registering objectives on the scoreboard..
     * and isn't needed after the wrapper's been baked.
     *
     * @param type                  the type of scoreboard to build. {@link ScoreboardType}
     * @param scoreboardInformation scoreboard information wrapper containing title
     * @param objectiveRegisterData the data required to register the objective on the created scoreboard for the scoreboard wrapper.
     * @return a nice tasty wrapper with all the objectives registered,title set, and elements displayed.
     */
    public ScoreboardWrapper bake(ScoreboardType type, ScoreboardInformation scoreboardInformation, ObjectiveRegisterData objectiveRegisterData);
}
