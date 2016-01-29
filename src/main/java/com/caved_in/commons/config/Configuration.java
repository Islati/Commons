package com.caved_in.commons.config;

public interface Configuration {

	public boolean hasSqlBackend();

	public void setMysqlBackend(boolean val);

	public String getMysqlHost();

	public void setMysqlHost(String host);

	public String getMysqlDatabaseName();

	public void setMysqlDatabaseName(String name);

	public String getMysqlPort();

	public void setMysqlPort(String port);

	public String getMysqlUsername();

	public void setMysqlUsername(String username);

	public String getMysqlPassword();

	public void setMysqlPassword(String password);

	public boolean trackOnlinePlayerStatus();

	public void setTrackOnlinePlayerStatus(boolean val);

	public String getServerName();

	public void setServerName(String name);

	public boolean registerCommands();

	public void registerCommands(boolean val);

	public boolean enableBukkitCommands();

	public void enableBukkitCommands(boolean val);

	public boolean enablePluginsCommand();

	public void enablePluginsCommand(boolean val);

	public boolean enableJoinMessages();

	public void enableJoinMessages(boolean val);

	public boolean enableLeaveMessages();

	public void enableLeaveMessages(boolean val);

	public boolean enableKickMessages();

	public void enableKickMessages(boolean val);

	public boolean hasExternalChatPlugin();

	public void externalChatPlugin(boolean val);

	public boolean isChatSilenced();

	public void silenceChat(boolean val);

	public boolean isPremiumOnlyMode();

	public void setPremiumOnlyMode(boolean val);

	public String getPremiumUserPermission();

	public void setPremiumUserPermission(String perm);

	public String getPremiumOnlyModeKickMessage();

	public void premiumOnlyModeKickMessage(String msg);

	public boolean kickNonPremiumPlayerWhenFull();

	public void kickNonPremiumPlayerWhenFull(boolean val);

	public String kickNonPremiumMessage();

	public void setKickNonPremiumMessage(String msg);

	public boolean teleportToSpawnOnJoin();

	public void teleportToSpawnOnJoin(boolean val);

	public boolean disableWeather();

	public void disableWeather(boolean val);

	public boolean disableLightning();

	public void disableLightning(boolean val);

	public boolean disableThunder();

	public void disableThunder(boolean val);

	public boolean disableIceAccumulation();

	public void disableIceAccumulation(boolean val);

	public boolean disableSnowAccumulation();

	public void disableSnowAccumulation(boolean val);

	public boolean disableMyceliumSpread();

	public void disableMyceliumSpread(boolean val);

	public boolean disableFireSpread();

	public void disableFireSpread(boolean val);

	public boolean hasLaunchpadPressurePlates();

	public void launchpadPressurePlates(boolean val);

	public boolean enableBlockBreak();

	public void enableBlockBreak(boolean val);

	public boolean enableItemPickup();

	public void enableItemPickup(boolean val);

	public boolean enableItemDrop();

	public void enableItemDrop(boolean val);

	public boolean enableFoodChange();

	public void enableFoodChange(boolean val);

	public boolean hasExplosionFireworks();

	public void explosionFireworks(boolean val);

	public boolean enableFallDamage();

	public void enableFallDamage(boolean val);

	public boolean isMaintenanceModeEnabled();

	public void setMaintenanceMode(boolean val);

	public String maintenanceModeKickMessage();

	public void maintenanceModeKickMessage(String msg);

	public String maintenanceModeMotd();

	public void maintenanceModeMotd(String msg);

	public boolean enableStackTraceEvent();

	public void enableStackTraceEvent(boolean val);

	public boolean enableStackTraceBook();

	public void enableStackTraceBook(boolean val);

	public boolean enableStackTraceChat();

	public void enableStackTraceChat(boolean val);

	public boolean enableWarpsMenu();

	public void enableWarpsMenu(boolean val);
}
