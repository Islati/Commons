package com.caved_in.commons.config;

import com.caved_in.commons.chat.Title;
import com.caved_in.commons.chat.TitleBuilder;
import org.bukkit.ChatColor;
import org.simpleframework.xml.Element;

public class SerializableTitle {
    @Element(name = "title-text")
    private String titleText;

    @Element(name = "subtitle-text")
    private String subTitleText;

    @Element(name = "fade-in-time")
    private int fadeIn;

    @Element(name = "stay-time")
    private int stayTime;

    @Element(name = "fade-out-time")
    private int fadeOut;

    @Element(name = "title-color")
    private String titleColorName;

    @Element(name = "subtitle-color")
    private String subtitleColorName;

    @Element(name = "time-in-ticks")
    private boolean timeInTicks = false;

    private ChatColor titleColor;

    private ChatColor subtitleColor;

    private TitleBuilder builder = new TitleBuilder();

    public SerializableTitle(Title title) {
        this.titleText = title.getTitle();
        this.subTitleText = title.getSubtitle();
        this.fadeIn = title.getFadeInTime();
        this.fadeOut = title.getFadeOutTime();
        this.stayTime = title.getStayTime();
        this.titleColor = title.getTitleColor();
        this.titleColorName = titleColor.name();
        this.subtitleColor = title.getSubtitleColor();
        this.subtitleColorName = subtitleColor.name();

        if (title.isTicks()) {
            timeInTicks = true;
        }
    }

    public SerializableTitle(@Element(name = "title-text") String titleText,
                             @Element(name = "subtitle-text") String subTitleText,
                             @Element(name = "fade-in-time") int fadeIn,
                             @Element(name = "stay-time") int stayTime,
                             @Element(name = "fade-out-time") int fadeOut,
                             @Element(name = "title-color") String titleColorName,
                             @Element(name = "subtitle-color") String subtitleColorName,
                             @Element(name = "time-in-ticks") boolean timeInTicks) {
        this.titleText = titleText;
        this.subTitleText = subTitleText;
        this.fadeIn = fadeIn;
        this.stayTime = stayTime;
        this.fadeOut = fadeOut;
        this.titleColorName = titleColorName;
        this.subtitleColorName = subtitleColorName;
        this.timeInTicks = timeInTicks;
        this.titleColor = ChatColor.valueOf(titleColorName);
        this.subtitleColor = ChatColor.valueOf(subtitleColorName);
        this.timeInTicks = timeInTicks;
    }

    public SerializableTitle(TitleBuilder builder) {
        this(builder.build());
    }

    public Title getTitle() {
        builder.title(titleText)
                .subtitle(subTitleText)
                .fadeIn(fadeIn)
                .stay(stayTime)
                .fadeOut(fadeOut)
                .titleColor(titleColor)
                .subtitleColor(subtitleColor);

        if (timeInTicks) {
            builder.ticks();
        } else {
            builder.seconds();
        }

        return builder.build();
    }
}
