package com.caved_in.commons.config;

import com.caved_in.commons.utilities.StringUtil;
import org.simpleframework.xml.Element;

public class PremiumConfiguration {
	@Element(name = "premium_enable")
	private boolean premiumMode = false;

	@Element(name = "premium_kick_message")
	private String premiumKickMessage = "&cThis server is currently in premium mode; Donate to join the server";

	@Element(name = "premium_only_permission", required = false)
	private String premiumOnlyPermission = "commons.premiumuser";

	@Element(name = "kick_non_premium_player_when_full", required = false)
	private boolean kickNonPremiumPlayerWhenFull = false;

	@Element(name = "kick_non_premium_message", required = false)
	private String kickNonPremiumMessage = "&eYou were kicked to make room for a Premium User. Sorry.";

	public PremiumConfiguration(
			@Element(name = "premium_kick_message") String premiumKickMessage,
			@Element(name = "premium_enable") boolean premiumEnable,
			@Element(name = "premium_only_permission", required = false) String premiumOnlyPermission,
			@Element(name = "kick_non_premium_player_when_full", required = false) boolean kickNonPremiumPlayerWhenFull,
			@Element(name = "kick_non_premium_message", required = false) String kickNonPremiumMessage
	) {
		this.premiumKickMessage = premiumKickMessage;
		this.premiumMode = premiumEnable;
		this.premiumOnlyPermission = premiumOnlyPermission;
		this.kickNonPremiumPlayerWhenFull = kickNonPremiumPlayerWhenFull;
		this.kickNonPremiumMessage = kickNonPremiumMessage;
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

	public String getPremiumOnlyPermission() {
		return premiumOnlyPermission;
	}

	public void setPremiumOnlyPermission(String premiumOnlyPermission) {
		this.premiumOnlyPermission = premiumOnlyPermission;
	}

	public boolean isKickNonPremiumPlayerWhenFull() {
		return kickNonPremiumPlayerWhenFull;
	}

	public void setKickNonPremiumPlayerWhenFull(boolean kickNonPremiumPlayerWhenFull) {
		this.kickNonPremiumPlayerWhenFull = kickNonPremiumPlayerWhenFull;
	}

	public String getKickNonPremiumMessage() {
		return kickNonPremiumMessage;
	}

	public void setKickNonPremiumMessage(String kickNonPremiumMessage) {
		this.kickNonPremiumMessage = kickNonPremiumMessage;
	}
}