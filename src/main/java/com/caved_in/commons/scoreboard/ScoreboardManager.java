package com.caved_in.commons.scoreboard;

import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.threading.RunnableManager;
import lombok.NonNull;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager implements BoardManager, Listener {
    protected BukkitPlugin plugin;
    private long delay;

    private ScoreboardWrapper defaultWrapper;

    /**
     * Represents a 16 character spacer string which can be used as a scoreboard entry.
     * Duplicate values will be dealt with in the new system.
     */
    public static final String SPACER_ENTRY = " ";

    private final Map<UUID, ScoreboardWrapper> data = new HashMap<>();

    public ScoreboardManager(BukkitPlugin plugin, long delay) {
        Validate.notNull(plugin, "The host plugin is null!");
        Validate.isTrue(delay > 0, "The scheduler delay must be positive.");
        this.plugin = plugin;
        this.delay = delay;

        plugin.registerListeners(this);
    }

    /**
     * Schedules multiple tasks to run under a parent plugin. All tasks will be for scoreboard text cycling.
     * This method may use any interval for the desired UI appearance, but the tasks must be repeating and executed on the main thread.
     *
     * @param tasks The tasks to schedule.
     */
    private void schedule(Collection<? extends Runnable> tasks) {
        RunnableManager manager = plugin.getThreadManager();
        for (Runnable runnable : tasks) {
            manager.registerSyncRepeatTask(UUID.randomUUID().toString(), runnable, delay, delay);
        }
    }

    public ScoreboardWrapper bake(ScoreboardType type, @NonNull ScoreboardInformation info, @NonNull ObjectiveRegisterData objectiveData) {
        Scoreboard scoreboard = Scoreboards.make();

        if (scoreboard == null) {
            return null;
        }

        //Register the objective to the scoreboard, allowing everything from the
        //scoreboard info wrapper to be displayed
        Objective objective = objectiveData.register(scoreboard);

        ScoreboardWrapper wrapper = null;
        switch (type) {
            case SCROLLING:
//				wrapper = new ScrollingScoreboardWrapper(this,scoreboard,info);
            case NORMAL:
                wrapper = new BasicScoreboardWrapper(this, scoreboard, info);
                break;
            default:
                break;
        }

        //Create the wrapper and assign the scoreboard info to it.
        wrapper.setDisplaySlot(objective.getDisplaySlot());

        return wrapper;
    }

    @Override
    public void resetScoreboard(@NonNull Player player) {
        player.setScoreboard(Scoreboards.main());
        data.remove(player.getUniqueId());
    }

    @Override
    public boolean hasData() {
        return !data.isEmpty();
    }

    @Override
    public boolean hasData(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public ScoreboardWrapper getWrapper(UUID id) {
        return data.get(id);
    }

    @Override
    public ScoreboardInformation getData(UUID id) {
        if (!hasData(id)) {
            return null;
        }
        return getWrapper(id).getInfo();
    }

    @Override
    public Scoreboard getScoreboard(UUID id) {
        if (!hasData(id)) {
            return null;
        }
        return getWrapper(id).getScoreboard();
    }

    @Override
    public BukkitPlugin getPlugin() {
        return plugin;
    }

    @Override
    public ScoreboardBuilder builder() {
        return new ScoreboardBuilder(this, DisplaySlot.SIDEBAR);
    }

    @Override
    public void assign(Player player, ScoreboardWrapper wrapper) {
        if (wrapper.hasThreads()) {
            schedule(wrapper.assign(player).getThreads());
        }

        data.put(player.getUniqueId(), wrapper);
    }

    @Override
    public void resetScoreboard(UUID id) {
        data.remove(id);
    }

    @Override
    public void setDefaultScoreboard(ScoreboardWrapper wrapper) {
        boolean setExisting = defaultWrapper == null;
        this.defaultWrapper = wrapper;
        //If there was no previously global scoreboard, then we need to assign the scoreboard to
        //all online players.

        if (setExisting) {
            for (Player p : Players.allPlayers()) {
                assign(p, defaultWrapper);
            }
        }
    }

    @Override
    public boolean hasDefaultScoreboard() {
        return defaultWrapper != null;
    }

    @Override
    public ScoreboardWrapper getDefaultWrapper() {
        return defaultWrapper;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        if (!hasDefaultScoreboard()) {
            return;
        }

        assign(e.getPlayer(), getDefaultWrapper());
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        data.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerKickEvent(PlayerKickEvent e) {
        data.remove(e.getPlayer().getUniqueId());
    }
}
