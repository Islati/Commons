package com.caved_in.commons.config;

import com.caved_in.commons.utilities.StringUtil;
import org.simpleframework.xml.Element;

public class PremiumConfiguration {

	@Element(name = "premium_kick_message")
	private String premiumKickMessage = "&cThis server is currently in premium mode; Donate to join the server";

	@Element(name = "premium_enable")
	private boolean premiumMode = false;

	public PremiumConfiguration(@Element(name = "premium_kick_message") String premiumKickMessage, @Element(name = "premium_enable") boolean premiumEnable) {
		this.premiumKickMessage = premiumKickMessage;
		this.premiumMode = premiumEnable;
	}

	public PremiumConfiguration() {

	}

	public boolean isPremiumMode() {
		return this.premiumMode;
	}

	public void setPremiumMode(boolean premium) {
		this.premiumMode = premium;
	}

	public void togglePremiumMode() {
		this.premiumMode = !this.premiumMode;
	}

	public String getKickMessage() {
		return StringUtil.formatColorCodes(this.premiumKickMessage);
	}

	public void setKickMessage(String message) {
		this.premiumKickMessage = message;
	}

}