package com.caved_in.commons.chat;

import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.ChatColor;

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

	public static TitleBuilder create() {
		return new TitleBuilder();
	}

	public TitleBuilder() {
	}

	public TitleBuilder title(String title) {
		this.title = StringUtil.colorize(title);
		return this;
	}

	public TitleBuilder subtitle(String subtitle) {
		this.subtitle = StringUtil.colorize(subtitle);
		return this;
	}

	public TitleBuilder titleColor(ChatColor color) {
		this.titleColor = color;
		return this;
	}

	public TitleBuilder subtitleColor(ChatColor color) {
		this.subtitleColor = color;
		return this;
	}

	public TitleBuilder fadeIn(int seconds) {
		this.fadeIn = seconds;
		return this;
	}

	public TitleBuilder stay(int seconds) {
		this.stays = seconds;
		return this;
	}

	public TitleBuilder fadeOut(int seconds) {
		this.fadeOut = seconds;
		return this;
	}

	public TitleBuilder ticks() {
		this.ticks = true;
		return this;
	}

	public TitleBuilder seconds() {
		this.ticks = false;
		return this;
	}


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
