package com.caved_in.commons.config;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class WorldConfiguration
{
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
							  @Element(name = "enableFoodChange") boolean enableFoodChange
	)
	{
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
	}

	public WorldConfiguration()
	{
	}

	public boolean isWeatherDisabled()
	{
		return this.disableWeather;
	}

	public boolean isChatSilenced()
	{
		return this.silenceChat;
	}

	public void setChatSilenced(boolean silenced)
	{
		this.silenceChat = silenced;
	}

	public boolean isLightningDisabled()
	{
		return this.disableLightning;
	}

	public boolean isThunderDisabled()
	{
		return this.disableThunder;
	}

	public boolean isIceSpreadDisabled()
	{
		return this.disableIceAccumulation;
	}

	public boolean isSnowSpreadDisabled()
	{
		return this.disableSnowAccumulation;
	}

	public boolean isMyceliumSpreadDisabled()
	{
		return this.disableMyceliumSpread;
	}

	public boolean hasLaunchpadPressurePlates()
	{
		return this.launchpadPressurePlates;
	}

	public boolean isCompassMenuEnabled()
	{
		return this.enableCompassMenu;
	}

	public boolean isJoinLeaveMessagesEnabled()
	{
		return this.enableJoinLeaveMessages;
	}

	public boolean hasExternalChatHandler()
	{
		return this.externalChatHandler;
	}

	public boolean isBlockBreakEnabled()
	{
		return this.enableBlockBreak;
	}

	public boolean isItemDropEnabled()
	{
		return enableItemDrop;
	}

	public boolean isItemPickupEnabled()
	{
		return enableItemPickup;
	}

	public boolean isFoodChangeEnabled()
	{
		return enableFoodChange;
	}
}