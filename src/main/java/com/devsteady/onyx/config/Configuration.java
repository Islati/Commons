package com.devsteady.onyx.config;

public interface Configuration {

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

	boolean enableStackTraceChat();

	void enableStackTraceChat(boolean val);

	boolean enableWarpsMenu();

	void enableWarpsMenu(boolean val);
}
