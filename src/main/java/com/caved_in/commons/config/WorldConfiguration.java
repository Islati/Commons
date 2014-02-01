package com.caved_in.commons.config;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class WorldConfiguration {
	@Element
	private boolean disableWeather = true;

	@Element
	private boolean disableLightning = true;

	@Element
	private boolean disableThunder = true;

	@Element
	private boolean disableIceAccumulation = true;

	@Element
	private boolean disableSnowAccumulation = true;

	@Element
	private boolean disableMyceliumSpread = true;

	@Element
	private boolean disableFireSpread = true;

	@Element
	private boolean launchpadPressurePlates = false;

	@Element
	private boolean enableCompassMenu = false;

	@Element
	private boolean enableJoinLeaveMessages = false;

	@Element
	private boolean enableBlockBreak = false;

	@Element
	private boolean enableItemPickup = false;

	@Element
	private boolean enableItemDrop = false;

	@Element
	private boolean enableFoodChange = false;

	@Element(name = "externalChatHandler")
	private boolean externalChatHandler = false;

	private boolean silenceChat = false;

	public WorldConfiguration(@Element(name = "disableWeather") boolean disableWeather,
							  @Element(name = "disableLightning") boolean disableLightning,
							  @Element(name = "disableThunder") boolean disableThunder,
							  @Element(name = "disableIceAccumulation") boolean disableIceAccumulation,
							  @Element(name = "disableSnowAccumulation") boolean disableSnowAccumulation,
							  @Element(name = "disableMyceliumSpread") boolean disableMyceliumSpread,
							  @Element(name = "launchpadPressurePlates") boolean launchpadPressurePlates,
							  @Element(name = "enableCompassMenu") boolean enableCompassMenu,
							  @Element(name = "enableJoinLeaveMessages") boolean enableJoinLeaveMessages,
							  @Element(name = "externalChatHandler") boolean externalChatHandler,
							  @Element(name = "enableBlockBreak") boolean enableBlockBreak,
							  @Element(name = "enableItemPickup") boolean enableItemPickup,
							  @Element(name = "enableItemDrop") boolean enableItemDrop,
							  @Element(name = "enableFoodChange") boolean enableFoodChange,
							  @Element(name = "disableFireSpread") boolean disableFireSpread
	) {
		this.disableIceAccumulation = disableIceAccumulation;
		this.disableWeather = disableWeather;
		this.disableLightning = disableLightning;
		this.disableMyceliumSpread = disableMyceliumSpread;
		this.disableSnowAccumulation = disableSnowAccumulation;
		this.disableThunder = disableThunder;
		this.launchpadPressurePlates = launchpadPressurePlates;
		this.enableCompassMenu = enableCompassMenu;
		this.enableJoinLeaveMessages = enableJoinLeaveMessages;
		this.externalChatHandler = externalChatHandler;
		this.enableBlockBreak = enableBlockBreak;
		this.enableItemDrop = enableItemDrop;
		this.enableItemPickup = enableItemPickup;
		this.enableFoodChange = enableFoodChange;
		this.disableFireSpread = disableFireSpread;
	}

	public WorldConfiguration() {
	}

	public boolean isWeatherDisabled() {
		return this.disableWeather;
	}

	public boolean isChatSilenced() {
		return this.silenceChat;
	}

	public void setChatSilenced(boolean silenced) {
		this.silenceChat = silenced;
	}

	public boolean isLightningDisabled() {
		return this.disableLightning;
	}

	public boolean isThunderDisabled() {
		return this.disableThunder;
	}

	/**
	 * @return true if ice spread is disabled, false otherwise
	 */
	public boolean isIceSpreadDisabled() {
		return this.disableIceAccumulation;
	}

	/**
	 * @return true if snow spread is disabled, false otherwise
	 */
	public boolean isSnowSpreadDisabled() {
		return this.disableSnowAccumulation;
	}

	/**
	 * @return true if mycelium spread is disabled, false otherwise
	 */
	public boolean isMyceliumSpreadDisabled() {
		return this.disableMyceliumSpread;
	}

	/**
	 * Whether or not this server has "launch-pad" pressure plates
	 *
	 * @return true if pressure plates are launch-pads, false otherwise
	 */
	public boolean hasLaunchpadPressurePlates() {
		return this.launchpadPressurePlates;
	}

	/**
	 * Check whether or not the menu for server-selection via compass is enabled
	 *
	 * @return true if the compass menu is enabled, false otherwise
	 */
	public boolean isCompassMenuEnabled() {
		return this.enableCompassMenu;
	}

	/**
	 * @return true if join / leave messages are enabled, false otherwise
	 */
	public boolean isJoinLeaveMessagesEnabled() {
		return this.enableJoinLeaveMessages;
	}

	/**
	 * @return true if there's an external plugin for chat handling, false if commons is handling
	 */
	public boolean hasExternalChatHandler() {
		return this.externalChatHandler;
	}

	/**
	 * @return true if players can break blocks, false otherwise
	 */
	public boolean isBlockBreakEnabled() {
		return this.enableBlockBreak;
	}

	/**
	 * @return true if players can drop items, false otherwise
	 */
	public boolean isItemDropEnabled() {
		return enableItemDrop;
	}

	/**
	 * @return true if players can pick up items, false otherwise
	 */
	public boolean isItemPickupEnabled() {
		return enableItemPickup;
	}

	/**
	 * @return true if food-change is enabled, false otherwise
	 */
	public boolean isFoodChangeEnabled() {
		return enableFoodChange;
	}

	/**
	 * @return true if firespread is disabled, false otherwise
	 */
	public boolean isFireSpreadDisabled() {
		return disableFireSpread;
	}
}