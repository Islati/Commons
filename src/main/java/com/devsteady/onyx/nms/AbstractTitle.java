package com.devsteady.onyx.nms;

import com.devsteady.onyx.time.TimeHandler;
import com.devsteady.onyx.time.TimeType;
import com.devsteady.onyx.utilities.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class AbstractTitle {
    /* Title text and color */
    private String title = null;
    private ChatColor titleColor = ChatColor.WHITE;
    /* Subtitle text and color */
    private String subtitle = null;
    private ChatColor subtitleColor = ChatColor.WHITE;
    /* Title timings */
    private int fadeInTime = -1;
    private int stayTime = -1;
    private int fadeOutTime = -1;
    private boolean ticks = false;

    /**
     * Create a new 1.8 displayName
     *
     * @param title Title
     */
    public AbstractTitle(String title) {
        this.title = title;
    }

    /**
     * Create a new 1.8 displayName
     *
     * @param title    Title text
     * @param subtitle Subtitle text
     */
    public AbstractTitle(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    /**
     * Copy 1.8 displayName
     *
     * @param title Title
     */
    public AbstractTitle(AbstractTitle title) {
// Copy displayName
        this.title = title.title;
        this.subtitle = title.subtitle;
        this.titleColor = title.titleColor;
        this.subtitleColor = title.subtitleColor;
        this.fadeInTime = title.fadeInTime;
        this.fadeOutTime = title.fadeOutTime;
        this.stayTime = title.stayTime;
        this.ticks = title.ticks;
    }

    /**
     * Create a new 1.8 displayName
     *
     * @param title       Title text
     * @param subtitle    Subtitle text
     * @param fadeInTime  Fade in time
     * @param stayTime    Stay on screen time
     * @param fadeOutTime Fade out time
     */
    public AbstractTitle(String title, String subtitle, int fadeInTime, int stayTime,
                         int fadeOutTime) {
        this.title = StringUtil.colorize(title);
        this.subtitle = StringUtil.colorize(subtitle);
        this.fadeInTime = fadeInTime;
        this.stayTime = stayTime;
        this.fadeOutTime = fadeOutTime;
    }

    /**
     * Set displayName text
     *
     * @param title Title
     */
    public void setTitle(String title) {
        this.title = StringUtil.colorize(title);
    }

    /**
     * Get displayName text
     *
     * @return Title text
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set subtitle text
     *
     * @param subtitle Subtitle text
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = StringUtil.colorize(subtitle);
    }

    /**
     * Get subtitle text
     *
     * @return Subtitle text
     */
    public String getSubtitle() {
        return this.subtitle;
    }

    /**
     * Set the displayName color
     *
     * @param color Chat color
     */
    public void setTitleColor(ChatColor color) {
        this.titleColor = color;
    }

    /**
     * Set the subtitle color
     *
     * @param color Chat color
     */
    public void setSubtitleColor(ChatColor color) {
        this.subtitleColor = color;
    }

    /**
     * Set displayName fade in time
     *
     * @param time Time
     */
    public void setFadeInTime(int time) {
        this.fadeInTime = time;
    }

    /**
     * Set displayName fade out time
     *
     * @param time Time
     */
    public void setFadeOutTime(int time) {
        this.fadeOutTime = time;
    }

    /**
     * Set displayName stay time
     *
     * @param time Time
     */
    public void setStayTime(int time) {
        this.stayTime = time;
    }

    /**
     * Set timings to ticks
     */
    public void setTimingsToTicks() {
        ticks = true;
    }

    /**
     * Set timings to seconds
     */
    public void setTimingsToSeconds() {
        ticks = false;
    }

    public ChatColor getTitleColor() {
        return titleColor;
    }

    public ChatColor getSubtitleColor() {
        return subtitleColor;
    }

    public int getFadeInTime() {
        if (ticks) {
            return (int) TimeHandler.getTimeInTicks(fadeInTime, TimeType.SECOND);
        }
        return fadeInTime;
    }

    public int getStayTime() {
        if (ticks) {
            return (int) TimeHandler.getTimeInTicks(stayTime, TimeType.SECOND);
        }
        return stayTime;
    }

    public int getFadeOutTime() {
        if (ticks) {
            return (int) TimeHandler.getTimeInTicks(fadeOutTime, TimeType.SECOND);
        }
        return fadeOutTime;
    }

    public boolean isTicks() {
        return ticks;
    }

    /**
     * Send the displayName to a player
     *
     * @param player Player
     */
    public abstract void send(Player player);

    /**
     * Broadcast the displayName to all players
     */
    public void broadcast() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            send(p);
        }
    }

    /**
     * Clear the displayName
     *
     * @param player Player
     */
    public abstract void clearTitle(Player player);

    /**
     * Reset the displayName settings
     *
     * @param player Player
     */

    public abstract void resetTitle(Player player);

    public interface TitleHandler {
        void send(Player player, AbstractTitle title);

        void resetTitle(Player player);

        void clearTitle(Player player);

//        void loadClasses();
    }
}
