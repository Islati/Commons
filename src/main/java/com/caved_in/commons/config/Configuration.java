package com.caved_in.commons.config;

public interface Configuration {

	boolean hasSqlBackend();

	void setMysqlBackend(boolean val);

	String getMysqlHost();

	void setMysqlHost(String host);

	String getMysqlDatabaseName();

	void setMysqlDatabaseName(String name);

	String getMysqlPort();

	void setMysqlPort(String port);

	String getMysqlUsername();

	void setMysqlUsername(String username);

	String getMysqlPassword();

	void setMysqlPassword(String password);

	boolean trackOnlinePlayerStatus();

	void setTrackOnlinePlayerStatus(boolean val);

	String getServerName();

	void setServerName(String name);

	boolean registerCommands();

	void registerCommands(boolean val);

	boolean enableBukkitCommands();

	void enableBukkitCommands(boolean val);

	boolean enablePluginsCommand();

	void enablePluginsCommand(boolean val);

	boolean enableJoinMessages();

	void enableJoinMessages(boolean val);

	boolean enableLeaveMessages();

	void enableLeaveMessages(boolean val);

	boolean enableKickMessages();

	void enableKickMessages(boolean val);

	boolean hasExternalChatPlugin();

	void externalChatPlugin(boolean val);

	boolean isChatSilenced();

	void silenceChat(boolean val);

	boolean isPremiumOnlyMode();

	void setPremiumOnlyMode(boolean val);

	String getPremiumUserPermission();

	void setPremiumUserPermission(String perm);

	String getPremiumOnlyModeKickMessage();

	void premiumOnlyModeKickMessage(String msg);

	boolean kickNonPremiumPlayerWhenFull();

	void kickNonPremiumPlayerWhenFull(boolean val);

	String kickNonPremiumMessage();

	void setKickNonPremiumMessage(String msg);

	boolean teleportToSpawnOnJoin();

	void teleportToSpawnOnJoin(boolean val);

	boolean disableWeather();

	void disableWeather(boolean val);

	boolean disableLightning();

	void disableLightning(boolean val);

	boolean disableThunder();

	void disableThunder(boolean val);

	boolean disableIceAccumulation();

	void disableIceAccumulation(boolean val);

	boolean disableSnowAccumulation();

	void disableSnowAccumulation(boolean val);

	boolean disableMyceliumSpread();

	void disableMyceliumSpread(boolean val);

	boolean disableFireSpread();

	void disableFireSpread(boolean val);

	boolean disableLeavesDecay();

	void disableLeavesDecay(boolean val);

	boolean hasLaunchpadPressurePlates();

	void launchpadPressurePlates(boolean val);

	boolean enableBlockBreak();

	void enableBlockBreak(boolean val);

	boolean enableItemPickup();

	void enableItemPickup(boolean val);

	boolean enableItemDrop();

	void enableItemDrop(boolean val);

	boolean enableFoodChange();

	void enableFoodChange(boolean val);

	boolean hasExplosionFireworks();

	void explosionFireworks(boolean val);

	boolean enableFallDamage();

	void enableFallDamage(boolean val);

	boolean isMaintenanceModeEnabled();

	void setMaintenanceMode(boolean val);

	String maintenanceModeKickMessage();

	void maintenanceModeKickMessage(String msg);

	String maintenanceModeMotd();

	void maintenanceModeMotd(String msg);

	boolean enableStackTraceEvent();

	void enableStackTraceEvent(boolean val);

	boolean enableStackTraceBook();

	void enableStackTraceBook(boolean val);

	boolean enableStackTraceChat();

	void enableStackTraceChat(boolean val);

	boolean enableWarpsMenu();

	void enableWarpsMenu(boolean val);
}
