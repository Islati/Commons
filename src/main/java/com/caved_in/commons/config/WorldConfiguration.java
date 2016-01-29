package com.caved_in.commons.config;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "world-config")
public class WorldConfiguration {
	@Element(name = "teleport-to-spawn-on-join", required = false)
	private boolean teleportToSpawnOnJoin = false;

	@Element(name = "disable-weather")
	private boolean disableWeather = false;

	@Element(name = "disable-lightning")
	private boolean disableLightning = false;

	@Element(name = "disable-thunder")
	private boolean disableThunder = false;

	@Element(name = "disable-ice-accumulation")
	private boolean disableIceAccumulation = false;

	@Element(name = "disable-snow-accumulation")
	private boolean disableSnowAccumulation = false;

	@Element(name = "disable-mycelium-spread")
	private boolean disableMyceliumSpread = false;

	@Element(name = "disable-fire-spread")
	private boolean disableFireSpread = false;

	@Element(name = "disable-leaf-decay", required = false)
	private boolean disableLeafDecay = false;

	@Element(name = "launchpad-pressure-plates")
	private boolean launchpadPressurePlates = false;

	@Element(name = "enable-join-messages")
	private boolean enableJoinMessages = true;

	@Element(name = "enable-leave-messages")
	private boolean enableLeaveMessages = true;

	@Element(name = "enable-kick-messages", required = false)
	private boolean enableKickMessages = true;

	@Element(name = "enable-block-break")
	private boolean enableBlockBreak = true;

	@Element(name = "enable-item-pickup")
	private boolean enableItemPickup = true;

	@Element(name = "enable-item-drop")
	private boolean enableItemDrop = true;

	@Element(name = "enable-food-change")
	private boolean enableFoodChange = true;

	@Element(name = "external-chat-plugin")
	private boolean externalChatHandler = true;

	@Element(name = "explosion-fireworks")
	private boolean explosionFireworks = false;

	@Element(name = "enable-fall-damage")
	private boolean fallDamage = true;

	@Element(name = "silence-chat", required = false)
	private boolean silenceChat = false;

	public WorldConfiguration(
			@Element(name = "teleport-to-spawn-on-join", required = false) boolean teleportToSpawnOnJoin,
			@Element(name = "disable-weather") boolean disableWeather,
			@Element(name = "disable-lightning") boolean disableLightning,
			@Element(name = "disable-thunder") boolean disableThunder,
			@Element(name = "disable-ice-accumulation") boolean disableIceAccumulation,
			@Element(name = "disable-snow-accumulation") boolean disableSnowAccumulation,
			@Element(name = "disable-mycelium-spread") boolean disableMyceliumSpread,
			@Element(name = "launchpad-pressure-plates") boolean launchpadPressurePlates,
			@Element(name = "enable-join-messages") boolean enableJoinMessages,
			@Element(name = "enable-leave-messages") boolean enableLeaveMessages,
			@Element(name = "enable-kick-messages", required = false) boolean enableKickMessages,
			@Element(name = "external-chat-plugin") boolean externalChatHandler,
			@Element(name = "enable-block-break") boolean enableBlockBreak,
			@Element(name = "enable-item-pickup") boolean enableItemPickup,
			@Element(name = "enable-item-drop") boolean enableItemDrop,
			@Element(name = "enable-food-change") boolean enableFoodChange,
			@Element(name = "disable-fire-spread") boolean disableFireSpread,
			@Element(name = "explosion-fireworks") boolean explosionFireworks,
			@Element(name = "enable-fall-damage") boolean fallDamage,
			@Element(name = "silence-chat", required = false) boolean silenceChat,
			@Element(name = "disable-leaf-decay", required = false) boolean disableLeafDecay
	) {
		this.teleportToSpawnOnJoin = teleportToSpawnOnJoin;
		this.disableIceAccumulation = disableIceAccumulation;
		this.disableWeather = disableWeather;
		this.disableLightning = disableLightning;
		this.disableMyceliumSpread = disableMyceliumSpread;
		this.disableSnowAccumulation = disableSnowAccumulation;
		this.disableThunder = disableThunder;
		this.launchpadPressurePlates = launchpadPressurePlates;
		this.enableJoinMessages = enableJoinMessages;
		this.enableLeaveMessages = enableLeaveMessages;
		this.enableKickMessages = enableKickMessages;
		this.externalChatHandler = externalChatHandler;
		this.enableBlockBreak = enableBlockBreak;
		this.enableItemDrop = enableItemDrop;
		this.enableItemPickup = enableItemPickup;
		this.enableFoodChange = enableFoodChange;
		this.disableFireSpread = disableFireSpread;
		this.explosionFireworks = explosionFireworks;
		this.fallDamage = fallDamage;
		this.silenceChat = silenceChat;
		this.disableLeafDecay = disableLeafDecay;
	}

	public WorldConfiguration() {
	}

	public boolean isTeleportToSpawnOnJoin() {
		return teleportToSpawnOnJoin;
	}

	public void setTeleportToSpawnOnJoin(boolean teleportToSpawnOnJoin) {
		this.teleportToSpawnOnJoin = teleportToSpawnOnJoin;
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
	 * Whether or not this server has "fire-pad" pressure plates
	 *
	 * @return true if pressure plates are fire-pads, false otherwise
	 */
	public boolean hasLaunchpadPressurePlates() {
		return this.launchpadPressurePlates;
	}

	/**
	 * @return true if join messages are enabled, false otherwise
	 */
	public boolean hasJoinMessages() {
		return enableJoinMessages;
	}

	/**
	 * Whether or not leave messages are enabled
	 *
	 * @return
	 */
	public boolean hasLeaveMessages() {
		return enableLeaveMessages;
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

	/**
	 * Whether or not creepers should display fireworks when they die.
	 *
	 * @return
	 */
	public boolean hasExplosionFireworks() {
		return explosionFireworks;
	}

	public boolean hasFallDamage() {
		return fallDamage;
	}

	public void setDisableWeather(boolean disableWeather) {
		this.disableWeather = disableWeather;
	}

	public void setDisableLightning(boolean disableLightning) {
		this.disableLightning = disableLightning;
	}

	public void setDisableThunder(boolean disableThunder) {
		this.disableThunder = disableThunder;
	}

	public void setDisableIceAccumulation(boolean disableIceAccumulation) {
		this.disableIceAccumulation = disableIceAccumulation;
	}

	public void setDisableSnowAccumulation(boolean disableSnowAccumulation) {
		this.disableSnowAccumulation = disableSnowAccumulation;
	}

	public void setDisableMyceliumSpread(boolean disableMyceliumSpread) {
		this.disableMyceliumSpread = disableMyceliumSpread;
	}

	public void setDisableFireSpread(boolean disableFireSpread) {
		this.disableFireSpread = disableFireSpread;
	}

	public void setLaunchpadPressurePlates(boolean launchpadPressurePlates) {
		this.launchpadPressurePlates = launchpadPressurePlates;
	}

	public void setEnableJoinMessages(boolean enableJoinMessages) {
		this.enableJoinMessages = enableJoinMessages;
	}

	public void setEnableLeaveMessages(boolean enableLeaveMessages) {
		this.enableLeaveMessages = enableLeaveMessages;
	}

	public void setEnableBlockBreak(boolean enableBlockBreak) {
		this.enableBlockBreak = enableBlockBreak;
	}

	public void setEnableItemPickup(boolean enableItemPickup) {
		this.enableItemPickup = enableItemPickup;
	}

	public void setEnableItemDrop(boolean enableItemDrop) {
		this.enableItemDrop = enableItemDrop;
	}

	public void setEnableFoodChange(boolean enableFoodChange) {
		this.enableFoodChange = enableFoodChange;
	}

	public void setExternalChatHandler(boolean externalChatHandler) {
		this.externalChatHandler = externalChatHandler;
	}

	public void setExplosionFireworks(boolean explosionFireworks) {
		this.explosionFireworks = explosionFireworks;
	}

	public void setFallDamage(boolean fallDamage) {
		this.fallDamage = fallDamage;
	}

	public boolean isEnableKickMessages() {
		return enableKickMessages;
	}

	public void setEnableKickMessages(boolean enableKickMessages) {
		this.enableKickMessages = enableKickMessages;
	}

	public boolean isDisableLeafDecay() {
		return disableLeafDecay;
	}

	public void setDisableLeafDecay(boolean disableLeafDecay) {
		this.disableLeafDecay = disableLeafDecay;
	}
}