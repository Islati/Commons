package com.caved_in.commons.config;

import org.simpleframework.xml.Element;

/**
 * Commons configuration.
 */
public class CommonsXmlConfiguration implements Configuration {

	@Element(name = "mysql-backend")
	private boolean sqlBackend = false;

	@Element(name = "database-config", type = SqlConfiguration.class)
	private SqlConfiguration sqlConfig;

	@Element(name = "register-commands")
	private boolean registerCommands = true;

	@Element(name = "command-config", type = CommandConfiguration.class)
	private CommandConfiguration commandConfig;

	@Element(name = "premium-config", type = PremiumConfiguration.class)
	private PremiumConfiguration premiumConfig;

	@Element(name = "world-config", type = WorldConfiguration.class)
	private WorldConfiguration worldConfig;

	@Element(name = "maintenance-config", type = MaintenanceConfiguration.class)
	private MaintenanceConfiguration maintenanceConfig;

	@Element(name = "debug-config", type = DebugConfig.class)
	private DebugConfig debugConfig;

	@Element(name = "warp-config", type = WarpConfig.class)
	private WarpConfig warpConfig;

	@Element(name = "server-name")
	private String serverName = "EDIT THIS";

	public CommonsXmlConfiguration(@Element(name = "world-config", type = WorldConfiguration.class) WorldConfiguration worldConfig,
								   @Element(name = "database-config", type = SqlConfiguration.class) SqlConfiguration sqlConfig,
								   @Element(name = "maintenance-config", type = MaintenanceConfiguration.class) MaintenanceConfiguration maintenanceConfig,
								   @Element(name = "server-name") String serverName,
								   @Element(name = "premium-config", type = PremiumConfiguration.class) PremiumConfiguration premiumConfig,
								   @Element(name = "mysql-backend") boolean sqlBackend,
								   @Element(name = "register-commands") boolean registerCommands,
								   @Element(name = "debug-config", type = DebugConfig.class) DebugConfig debugConfig,
								   @Element(name = "warp-config", type = WarpConfig.class) WarpConfig warpConfig,
								   @Element(name = "command-config", type = CommandConfiguration.class) CommandConfiguration commandConfig) {
		this.worldConfig = worldConfig;
		this.sqlConfig = sqlConfig;
		this.maintenanceConfig = maintenanceConfig;
		this.serverName = serverName;
		this.premiumConfig = premiumConfig;
		this.sqlBackend = sqlBackend;
		this.registerCommands = registerCommands;
		this.debugConfig = debugConfig;
		this.warpConfig = warpConfig;
		this.commandConfig = commandConfig;
	}

	public CommonsXmlConfiguration() {
		this.worldConfig = new WorldConfiguration();
		this.sqlConfig = new SqlConfiguration();
		this.maintenanceConfig = new MaintenanceConfiguration();
		this.premiumConfig = new PremiumConfiguration();
		this.debugConfig = new DebugConfig();
		this.warpConfig = new WarpConfig();
		this.commandConfig = new CommandConfiguration();
	}

	public MaintenanceConfiguration getMaintenanceConfig() {
		return maintenanceConfig;
	}

	public WorldConfiguration getWorldConfig() {
		return worldConfig;
	}

	public SqlConfiguration getSqlConfig() {
		return sqlConfig;
	}

	@Override
	public String getServerName() {
		return serverName;
	}

	@Override
	public void setServerName(String name) {
		serverName = name;
	}

	public PremiumConfiguration getPremiumConfig() {
		return premiumConfig;
	}

	@Override
	public boolean hasSqlBackend() {
		return sqlBackend;
	}

	@Override
	public void setMysqlBackend(boolean val) {
		sqlBackend = val;
	}

	@Override
	public String getMysqlHost() {
		return sqlConfig.getHost();
	}

	@Override
	public void setMysqlHost(String host) {
		sqlConfig.setMySqlHost(host);
	}

	@Override
	public String getMysqlDatabaseName() {
		return sqlConfig.getDatabase();
	}

	@Override
	public void setMysqlDatabaseName(String name) {
		sqlConfig.setMySqlDatabaseName(name);
	}

	@Override
	public String getMysqlPort() {
		return sqlConfig.getPort();
	}

	@Override
	public void setMysqlPort(String port) {
		sqlConfig.setMySqlPort(port);
	}

	@Override
	public String getMysqlUsername() {
		return sqlConfig.getUsername();
	}

	@Override
	public void setMysqlUsername(String username) {
		sqlConfig.setMySqlUsername(username);
	}

	@Override
	public String getMysqlPassword() {
		return sqlConfig.getPassword();
	}

	@Override
	public void setMysqlPassword(String password) {
		sqlConfig.getPassword();
	}

	@Override
	public boolean trackOnlinePlayerStatus() {
		return sqlConfig.trackPlayerOnlineStatus();
	}

	@Override
	public void setTrackOnlinePlayerStatus(boolean val) {
		sqlConfig.setTrackPlayerOnlineStatus(val);
	}

	@Override
	public boolean registerCommands() {
		return registerCommands;
	}

	@Override
	public void registerCommands(boolean val) {
		registerCommands = val;
	}

	@Override
	public boolean enableBukkitCommands() {
		return !commandConfig.disableBukkitCommands();
	}

	@Override
	public void enableBukkitCommands(boolean val) {
		commandConfig.setDisableBukkitCommands(!val);
	}

	@Override
	public boolean enablePluginsCommand() {
		return !commandConfig.disablePluginsCommand();
	}

	@Override
	public void enablePluginsCommand(boolean val) {
		commandConfig.setDisablePluginsCommand(!val);
	}

	@Override
	public boolean enableJoinMessages() {
		return worldConfig.hasJoinMessages();
	}

	@Override
	public void enableJoinMessages(boolean val) {
		worldConfig.setEnableJoinMessages(val);
	}

	@Override
	public boolean enableLeaveMessages() {
		return worldConfig.hasLeaveMessages();
	}

	@Override
	public void enableLeaveMessages(boolean val) {
		worldConfig.setEnableLeaveMessages(val);
	}

	@Override
	public boolean enableKickMessages() {
		return worldConfig.isEnableKickMessages();
	}

	@Override
	public void enableKickMessages(boolean val) {
		worldConfig.setEnableKickMessages(val);
	}

	@Override
	public boolean hasExternalChatPlugin() {
		return worldConfig.hasExternalChatHandler();
	}

	@Override
	public void externalChatPlugin(boolean val) {
		worldConfig.setExternalChatHandler(val);
	}

	@Override
	public boolean isChatSilenced() {
		return worldConfig.isChatSilenced();
	}

	@Override
	public void silenceChat(boolean val) {
		worldConfig.setChatSilenced(val);
	}

	@Override
	public boolean isPremiumOnlyMode() {
		return premiumConfig.isPremiumMode();
	}

	@Override
	public void setPremiumOnlyMode(boolean val) {
		premiumConfig.setPremiumMode(val);
	}

	@Override
	public String getPremiumUserPermission() {
		return premiumConfig.getPremiumOnlyPermission();
	}

	@Override
	public void setPremiumUserPermission(String perm) {
		premiumConfig.setPremiumOnlyPermission(perm);
	}

	@Override
	public String getPremiumOnlyModeKickMessage() {
		return premiumConfig.getKickMessage();
	}

	@Override
	public void premiumOnlyModeKickMessage(String msg) {
		premiumConfig.setKickMessage(msg);
	}

	@Override
	public boolean kickNonPremiumPlayerWhenFull() {
		return premiumConfig.isKickNonPremiumPlayerWhenFull();
	}

	@Override
	public void kickNonPremiumPlayerWhenFull(boolean val) {
		premiumConfig.setKickNonPremiumPlayerWhenFull(val);
	}

	@Override
	public String kickNonPremiumMessage() {
		return premiumConfig.getKickNonPremiumMessage();
	}

	@Override
	public void setKickNonPremiumMessage(String msg) {
		premiumConfig.setKickNonPremiumMessage(msg);
	}

	@Override
	public boolean teleportToSpawnOnJoin() {
		return worldConfig.isTeleportToSpawnOnJoin();
	}

	@Override
	public void teleportToSpawnOnJoin(boolean val) {
		worldConfig.setTeleportToSpawnOnJoin(val);
	}

	@Override
	public boolean disableWeather() {
		return worldConfig.isWeatherDisabled();
	}

	@Override
	public void disableWeather(boolean val) {
		worldConfig.setDisableWeather(val);
	}

	@Override
	public boolean disableLightning() {
		return worldConfig.isLightningDisabled();
	}

	@Override
	public void disableLightning(boolean val) {
		worldConfig.setDisableLightning(val);
	}

	@Override
	public boolean disableThunder() {
		return worldConfig.isThunderDisabled();
	}

	@Override
	public void disableThunder(boolean val) {
		worldConfig.setDisableThunder(val);
	}

	@Override
	public boolean disableIceAccumulation() {
		return worldConfig.isIceSpreadDisabled();
	}

	@Override
	public void disableIceAccumulation(boolean val) {
		worldConfig.setDisableIceAccumulation(val);
	}

	@Override
	public boolean disableSnowAccumulation() {
		return worldConfig.isSnowSpreadDisabled();
	}

	@Override
	public void disableSnowAccumulation(boolean val) {
		worldConfig.isSnowSpreadDisabled();
	}

	@Override
	public boolean disableMyceliumSpread() {
		return worldConfig.isMyceliumSpreadDisabled();
	}

	@Override
	public void disableMyceliumSpread(boolean val) {
		worldConfig.setDisableMyceliumSpread(val);
	}

	@Override
	public boolean disableFireSpread() {
		return worldConfig.isFireSpreadDisabled();
	}

	@Override
	public void disableFireSpread(boolean val) {
		worldConfig.setDisableFireSpread(val);
	}

	@Override
	public boolean hasLaunchpadPressurePlates() {
		return worldConfig.hasLaunchpadPressurePlates();
	}

	@Override
	public void launchpadPressurePlates(boolean val) {
		worldConfig.setLaunchpadPressurePlates(val);
	}

	@Override
	public boolean enableBlockBreak() {
		return worldConfig.isBlockBreakEnabled();
	}

	@Override
	public void enableBlockBreak(boolean val) {
		worldConfig.setEnableBlockBreak(val);
	}

	@Override
	public boolean enableItemPickup() {
		return worldConfig.isItemPickupEnabled();
	}

	@Override
	public void enableItemPickup(boolean val) {
		worldConfig.setEnableItemPickup(val);
	}

	@Override
	public boolean enableItemDrop() {
		return worldConfig.isItemDropEnabled();
	}

	@Override
	public void enableItemDrop(boolean val) {
		worldConfig.isItemDropEnabled();
	}

	@Override
	public boolean enableFoodChange() {
		return worldConfig.isFoodChangeEnabled();
	}

	@Override
	public void enableFoodChange(boolean val) {
		worldConfig.setEnableFoodChange(val);
	}

	@Override
	public boolean hasExplosionFireworks() {
		return worldConfig.hasExplosionFireworks();
	}

	@Override
	public void explosionFireworks(boolean val) {
		worldConfig.setExplosionFireworks(val);
	}

	@Override
	public boolean enableFallDamage() {
		return worldConfig.hasFallDamage();
	}

	@Override
	public void enableFallDamage(boolean val) {
		worldConfig.setFallDamage(val);
	}

	@Override
	public boolean isMaintenanceModeEnabled() {
		return maintenanceConfig.isMaintenanceMode();
	}

	@Override
	public void setMaintenanceMode(boolean val) {
		maintenanceConfig.setMaintenanceMode(val);
	}

	@Override
	public String maintenanceModeKickMessage() {
		return maintenanceConfig.getKickMessage();
	}

	@Override
	public void maintenanceModeKickMessage(String msg) {
		maintenanceConfig.setKickMessage(msg);
	}

	@Override
	public String maintenanceModeMotd() {
		return maintenanceConfig.getMotd();
	}

	@Override
	public void maintenanceModeMotd(String msg) {
		maintenanceConfig.setMotd(msg);
	}

	@Override
	public boolean enableStackTraceEvent() {
		return debugConfig.isStackTraceEvent();
	}

	@Override
	public void enableStackTraceEvent(boolean val) {
		debugConfig.setStackTraceEvent(val);
	}

	@Override
	public boolean enableStackTraceBook() {
		return debugConfig.isStackTraceBooks();
	}

	@Override
	public void enableStackTraceBook(boolean val) {
		debugConfig.setStackTraceBooks(val);
	}

	@Override
	public boolean enableStackTraceChat() {
		return debugConfig.isStackTraceChat();
	}

	@Override
	public void enableStackTraceChat(boolean val) {
		debugConfig.setStackTraceChat(val);
	}

	@Override
	public boolean enableWarpsMenu() {
		return warpConfig.isWarpsMenuEnabled();
	}

	@Override
	public void enableWarpsMenu(boolean val) {
		warpConfig.setWarpsMenuEnabled(val);
	}

	public DebugConfig getDebugConfig() {
		return debugConfig;
	}

	public WarpConfig getWarpConfig() {
		return warpConfig;
	}

	public CommandConfiguration getCommandConfig() {
		return commandConfig;
	}
}
