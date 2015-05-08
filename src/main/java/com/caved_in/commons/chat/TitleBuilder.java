package com.caved_in.commons.chat;

import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.ChatColor;

/**
 * Create a(n) {@link Title} with an easy, intuitive builder interface.
 */
public class TitleBuilder {
    private String title = null;
    private String subtitle = null;

    private ChatColor titleColor = ChatColor.WHITE;
    private ChatColor subtitleColor = ChatColor.WHITE;

    private int fadeIn = 0;
    private int fadeOut = 0;
    private int stays = 0;

    private boolean ticks = false;

    private Title titleObj = null;

    /**
     * Static-Syntatic-sugar for creating a new TitleBuilder.
     *
     * @return a new instance of TitleBuilder, with no values assigned.
     */
    public static TitleBuilder create() {
        return new TitleBuilder();
    }

    public TitleBuilder() {
    }

    /**
     * Title to assign to the builder. Automatically colorizes color-codes.
     *
     * @param title top line to display on the Title.
     * @return the title-builder
     */
    public TitleBuilder title(String title) {
        this.title = StringUtil.colorize(title);
        return this;
    }

    /**
     * Assign a subtitle to the Title object being created. Automatically colorizes color-codes
     *
     * @param subtitle bottom line to display on the Title.
     * @return the title-builder.
     */
    public TitleBuilder subtitle(String subtitle) {
        this.subtitle = StringUtil.colorize(subtitle);
        return this;
    }

    /**
     * Assign a color to the top-line of the Title.
     *
     * @param color color to assign to the top line of text.
     * @return the title-builder
     */
    public TitleBuilder titleColor(ChatColor color) {
        this.titleColor = color;
        return this;
    }

    /**
     * Assign a color to the bottom-line of the Title.
     *
     * @param color color to assign to the bottom line of text.
     * @return the title-builder.
     */
    public TitleBuilder subtitleColor(ChatColor color) {
        this.subtitleColor = color;
        return this;
    }

    /**
     * Assign the fade-in time in seconds for the Title.
     *
     * @param seconds amount of seconds the title will fade in for, before showing fully.
     * @return the title-builder
     */
    public TitleBuilder fadeIn(int seconds) {
        this.fadeIn = seconds;
        return this;
    }

    /**
     * Assign the time a title will stay on screen before proceeding to fade out.
     *
     * @param seconds seconds the title will stay on screen for.
     * @return the title-builder.
     */
    public TitleBuilder stay(int seconds) {
        this.stays = seconds;
        return this;
    }

    /**
     * Assign the time a title will take to fade out before disappearing on screen.
     *
     * @param seconds seconds the title will take to fade out
     * @return the title-builder.
     */
    public TitleBuilder fadeOut(int seconds) {
        this.fadeOut = seconds;
        return this;
    }

    /**
     * Instead of using seconds to measure the fade-in, stay, and fade-out time, use ticks.
     *
     * @return the title-builder.
     */
    public TitleBuilder ticks() {
        this.ticks = true;
        return this;
    }

    /**
     * Instead of using ticks to measure the fade-in, stay, and fade-out time, use seconds.
     *
     * @return the title-builder.
     */
    public TitleBuilder seconds() {
        this.ticks = false;
        return this;
    }


    /**
     * Create a new {@link Title} object from the builder.
     *
     * @return a new {@link Title} object from the builder.
     */
    public Title build() {
        if (title == null) {
            title = "";
        }

        if (subtitle == null) {
            subtitle = "";
        }

        if (titleObj == null) {
            titleObj = new Title(title, subtitle);
        } else {
            titleObj.setTitle(title);
            titleObj.setSubtitle(subtitle);
        }
        titleObj.setTitleColor(titleColor);
        titleObj.setSubtitleColor(subtitleColor);
        titleObj.setFadeInTime(fadeIn);
        titleObj.setFadeOutTime(fadeOut);
        titleObj.setStayTime(stays);

        if (ticks) {
            titleObj.setTimingsToTicks();
        } else {
            titleObj.setTimingsToSeconds();
        }

        return titleObj;
    }
}
