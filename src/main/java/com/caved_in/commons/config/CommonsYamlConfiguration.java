package com.caved_in.commons.config;

import com.caved_in.commons.yml.*;

@SerializeOptions(
		configHeader = {
				"Any concerns regarding the purpose of configuration nodes",
				"What they affect, or how they change aspects of the API",
				"are described under the Wiki on Commons GitHub page."
		},
		configMode = ConfigMode.DEFAULT
)

public class CommonsYamlConfiguration extends YamlConfig implements Configuration {
	@Path("Database.Mysql.enable")
	private boolean sqlBackend = false;

	@Path("Database.Mysql.host")
	private String mysqlHost = "localhost";

	@Path("Database.Mysql.port")
	private String mysqlPort = "3306";

	@Path("Database.Mysql.database-name")
	private String mysqlDatabaseName = "minecraft";

	@Path("Database.Mysql.username")
	private String mysqlUsername = "username";

	@Path("Database.Mysql.password")
	private String mysqlPassword;

	@Path("Database.track-online-status")
	//todo implement optional var in config. If its not present it's ok.
	private boolean trackOnlinePlayerStatus = false;

	@Path("Database.server-name")
	@Comments({
			"Used in the chosen database implementation",
			"to identify the server.",
	})
	private String serverName = "EDIT THIS";

	@Path("Commands.register-commands")
	@Comments({
			"By default Commons includes a plethora of commands",
			"Designed to aid you in your server ventures!",
			"Though if you're not requiring use of these commands, and",
			"Wish to use Commons for only its API Features, then change this value to",
			"False"
	})
	private boolean registerCommands = true;

	@Path("Commands.enable-bukkit-commands")
	@Comments({
			"Allows usage of 'Bukkit:' prefixed commands",
			"Changing this value to false disable these commands",
			"from being used on your server."
	})
	private boolean bukkitCommands = true;

	@Path("Commands.enable-plugins-command")
	@Comments({
			"Changing the value of this option to false",
			"Stops players from using '/plugins' on your server."
	})
	private boolean pluginsCommand = true;

	@Path("Server.enable-join-message")
	@Comments({
			"Whether or not to enable join messages",
			"in chat when a player joins the server"
	})
	private boolean enableJoinMessages = true;

	@Path("Server.enable-leave-messages")
	@Comments({
			"Whether or not to enable leave messages",
			"in chat when a player leaves the server"
	})
	private boolean enableLeaveMessages = true;

	@Path("Server.enable-kick-messages")
	@Comments({
			"Whether or not to show 'player was kicked'",
			"messages in chat, when a player is kicked."
	})
	private boolean enableKickMessages = true;

	@Path("Server.external-chat-plugin")
	@Comments({
			"Determines whether or not Commons should",
			"handle chat formatting (in a very basic manner)",
			"or to hand it off to another plugin"
	})
	private boolean externalChatPlugin = true;

	@Path("Server.silence-chat")
	@Comments({
			"When enabled, only players with 'commons.silence.bypass'",
			"in their permissions will be able to talk"
	})
	private boolean silenceChat = false;

	@Path("Server.Premium.kick-when-full")
	@Comments({
			"Determines whether or not to kick a non-premium player",
			"when a premium user joins the server, and the server",
			"is currently full.",
			"Premium users are determined by the 'premium-user-permission'",
			"node below."
	})
	private boolean kickNonPremiumPlayerWhenFull = false;

	@Path("Server.Premium.kick-when-full-message")
	@Comments({
			"Message to display to non-premium users after being",
			"kicked to make room for a premium user."
	})
	private String kickNonPremiumMessage = "&eYou were kicked to make room for a Premium User. Sorry.";


	@Path("Server.Premium.premium-only-mode")
	@Comments({
			"When enabled, only users with premium (defined by a permission below)",
			"will be able to join your server.",
			"Those without premium will be displayed a configurable message"
	})
	private boolean premiumOnlyMode = false;

	@Path("Server.Premium.premium-only-mode-kick-message")
	@Comment("This message will be shown to non-premium users who join during premium-only mode.")
	private String premiumOnlyModeKickMessage = "&cThis server is currently in premium mode";

	@Path("Server.Premium.premium-user-permission")
	@Comments({
			"Used to restrict access during premium only mode",
			"To players who have this permission"
	})
	private String premiumModePermission = "commons.premiumuser";

	@Path("Server.Worlds.disable-weather")
	@Comments({
			"All the options beneath this are used to control",
			"various aspects of the worlds across all",
			"the enabled worlds on your server.",
			"",
			"If you have another plugin enabled that also",
			"Modifies any of these values, there's no guarantee",
			"that they will function as expected."
	})
	private boolean disableWeather = false;

	@Path("Server.Worlds.teleport-to-spawn-on-join")
	@Comment("When enabled, players will be teleported to their world spawn when joining the server")
	private boolean teleportToSpawnOnJoin = false;

	@Path("Server.Worlds.disable-lightning")
	@Comment("Changes whether or not lightning will strike during a storm")
	private boolean disableLightning = false;

	@Path("Server.Worlds.disable-thunder")
	@Comment("Changes whether or not thunder will rumble during a storm")
	private boolean disableThunder = false;

	@Path("Server.Worlds.disable-ice-accumulation")
	@Comment("Changes whether or not ice will spread and accumulate")
	private boolean disableIceAccumulation = false;

	@Path("Server.Worlds.disable-snow-accumulation")
	@Comment("Changes whether or not snow will accumulate while snowing")
	private boolean disableSnowAccumulation = false;

	@Path("Server.Worlds.disable-mycelium-spread")
	@Comment("Changes whether or not mycelium will infect blocks around it, and spread")
	private boolean disableMyceliumSpread = false;

	@Path("Server.Worlds.disable-fire-spread")
	@Comment("Changes whether or not fire will spread")
	private boolean disableFireSpread = false;

	@Path("Server.Worlds.disable-leaf-decay")
	@Comment("Changes whether or not leaves will decay over time")
	private boolean disableLeafDecay = false;

	@Path("Server.Worlds.launchpad-pressure-plates")
	@Comment("When enabled it changes pressure plates into launch pads, like many server hubs have")
	private boolean launchpadPressurePlates = false;

	@Path("Server.Worlds.enable-block-break")
	@Comment("Changes whether or not blocks can be broken outside of creative")
	private boolean enableBlockBreak = true;

	@Path("Server.Worlds.enable-item-pickup")
	@Comment("Changes if players are able to pick up items that are dropped")
	private boolean enableItemPickup = true;

	@Path("Server.Worlds.enable-item-drop")
	@Comment("Changes if players are able to drop their items")
	private boolean enableItemDrop = true;

	@Path("Server.Worlds.enable-food-change")
	@Comments("Changes whether or not players lose their hunger while playing")
	private boolean enableFoodChange = true;

	@Path("Server.Worlds.fireworks-on-explosion")
	@Comment("When enabled, fireworks will launch and explode whenever a regular explosion happens")
	private boolean explosionFireworks = false;

	@Path("Server.Worlds.enable-fall-damage")
	@Comment("Changes whether or not players take fall damage")
	private boolean enableFallDamage = true;

	@Path("Server.Maintenance-Mode.enabled")
	@Comments({
			"Maintenance mode enables admins, operators, and users",
			"with the 'commons.maintenance.join' permission",
			"to join while the server is undergoing maintenance.",
			"At the same time, it keeps all players not permitted, out, until",
			"maintenance is complete!",
			"",
			"Customizable MOTD (Server list message)",
			"and kick message are available to notify users of",
			"maintenance!"
	})
	private boolean maintenanceMode = false;

	@Path("Server.Maintenance-Mode.kick-message")
	private String maintenanceModeKickMessage = "&cThis server is currently undergoing maintenance; Sorry for the inconvenience";

	@Path("Server.Maintenance-Mode.motd")
	private String maintenanceModeMotd = "&aThis server is currently undergoing maintenance";

	@Path("Debug.stack-trace-event")
	@Comments({
			"Debug options are very useful to developers!",
			"Providing a StackTraceEvent, and various output options",
			"which enable in-game players in debug mode",
			"and developers hooking the event to",
			"track, handle, change, and work with the headaches of bug fixing",
			"in an easy and fun manner!"
	})
	private boolean stackTraceEvent = true;

	@Path("Debug.stack-trace-book")
	@Comments({
			"When enabled in conjunction with stack-trace-event",
			"users in debug mode will receive a Book in-game outlining",
			"The error which happened, and it's stack trace written in the books",
			"pages!"
	})
	private boolean stackTraceBook = false;

	@Path("Debug.stack-trace-chat")
	@Comments({
			"When enabled in conjunction with stack-trace-event,",
			"users in debug mode will receive the stack trace in their chat;",
			"so eyes don't have to stray from game, to console, to code.",
			"",
			"Note: Can quickly and painfully spam your chat if to many",
			"errors occur"
	})
	private boolean stackTraceChat = true;

	@Path("Warps.enable-gui")
	@Comments({
			"When enabled, it provides an interactive GUI",
			"of which players can use to teleport and interact",
			"with warps.",
			"",
			"If it's disabled, and a player does /warps, they'll receive",
			"a chat based menu with pages detailing the available warps."
	})
	private boolean enableWarpsMenu = true;

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
		return mysqlHost;
	}

	@Override
	public void setMysqlHost(String host) {
		mysqlHost = host;
	}

	@Override
	public String getMysqlDatabaseName() {
		return mysqlDatabaseName;
	}

	@Override
	public void setMysqlDatabaseName(String name) {
		mysqlDatabaseName = name;
	}

	@Override
	public String getMysqlPort() {
		return mysqlPort;
	}

	@Override
	public void setMysqlPort(String port) {
		mysqlPort = port;
	}

	@Override
	public String getMysqlUsername() {
		return mysqlUsername;
	}

	@Override
	public void setMysqlUsername(String username) {
		mysqlUsername = username;
	}

	@Override
	public String getMysqlPassword() {
		return mysqlPassword;
	}

	@Override
	public void setMysqlPassword(String password) {
		mysqlPassword = password;
	}

	@Override
	public boolean trackOnlinePlayerStatus() {
		return trackOnlinePlayerStatus;
	}

	@Override
	public void setTrackOnlinePlayerStatus(boolean val) {
		trackOnlinePlayerStatus = val;
	}

	@Override
	public String getServerName() {
		return serverName;
	}

	@Override
	public void setServerName(String name) {
		serverName = name;
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
		return bukkitCommands;
	}

	@Override
	public void enableBukkitCommands(boolean val) {
		bukkitCommands = val;
	}

	@Override
	public boolean enablePluginsCommand() {
		return pluginsCommand;
	}

	@Override
	public void enablePluginsCommand(boolean val) {
		pluginsCommand = val;
	}

	@Override
	public boolean enableJoinMessages() {
		return enableJoinMessages;
	}

	@Override
	public void enableJoinMessages(boolean val) {
		enableJoinMessages = val;
	}

	@Override
	public boolean enableLeaveMessages() {
		return enableLeaveMessages;
	}

	@Override
	public void enableLeaveMessages(boolean val) {
		enableLeaveMessages = val;
	}

	@Override
	public boolean enableKickMessages() {
		return enableKickMessages;
	}

	@Override
	public void enableKickMessages(boolean val) {
		enableKickMessages = val;
	}

	@Override
	public boolean hasExternalChatPlugin() {
		return externalChatPlugin;
	}

	@Override
	public void externalChatPlugin(boolean val) {
		externalChatPlugin = val;
	}

	@Override
	public boolean isChatSilenced() {
		return silenceChat;
	}

	@Override
	public void silenceChat(boolean val) {
		silenceChat = val;
	}

	@Override
	public boolean isPremiumOnlyMode() {
		return premiumOnlyMode;
	}

	@Override
	public void setPremiumOnlyMode(boolean val) {
		premiumOnlyMode = val;
	}

	@Override
	public String getPremiumUserPermission() {
		return premiumModePermission;
	}

	@Override
	public void setPremiumUserPermission(String perm) {
		premiumModePermission = perm;
	}

	@Override
	public String getPremiumOnlyModeKickMessage() {
		return premiumOnlyModeKickMessage;
	}

	@Override
	public void premiumOnlyModeKickMessage(String msg) {
		premiumOnlyModeKickMessage = msg;
	}

	@Override
	public boolean kickNonPremiumPlayerWhenFull() {
		return kickNonPremiumPlayerWhenFull;
	}

	@Override
	public void kickNonPremiumPlayerWhenFull(boolean val) {
		kickNonPremiumPlayerWhenFull = val;
	}

	@Override
	public String kickNonPremiumMessage() {
		return kickNonPremiumMessage;
	}

	@Override
	public void setKickNonPremiumMessage(String msg) {
		kickNonPremiumMessage = msg;
	}

	@Override
	public boolean teleportToSpawnOnJoin() {
		return teleportToSpawnOnJoin;
	}

	@Override
	public void teleportToSpawnOnJoin(boolean val) {
		teleportToSpawnOnJoin = val;
	}

	@Override
	public boolean disableWeather() {
		return disableWeather;
	}

	@Override
	public void disableWeather(boolean val) {
		disableWeather = val;
	}

	@Override
	public boolean disableLightning() {
		return disableLightning;
	}

	@Override
	public void disableLightning(boolean val) {
		disableLightning = val;
	}

	@Override
	public boolean disableThunder() {
		return disableThunder;
	}

	@Override
	public void disableThunder(boolean val) {
		disableThunder = val;
	}

	@Override
	public boolean disableIceAccumulation() {
		return disableIceAccumulation;
	}

	@Override
	public void disableIceAccumulation(boolean val) {
		disableIceAccumulation = val;
	}

	@Override
	public boolean disableSnowAccumulation() {
		return disableSnowAccumulation;
	}

	@Override
	public void disableSnowAccumulation(boolean val) {
		disableSnowAccumulation = val;
	}

	@Override
	public boolean disableMyceliumSpread() {
		return disableMyceliumSpread;
	}

	@Override
	public void disableMyceliumSpread(boolean val) {
		disableMyceliumSpread = val;
	}

	@Override
	public boolean disableFireSpread() {
		return disableFireSpread;
	}

	@Override
	public void disableFireSpread(boolean val) {
		disableFireSpread = val;
	}

	@Override
	public boolean disableLeavesDecay() {
		return disableLeafDecay;
	}

	@Override
	public void disableLeavesDecay(boolean val) {
		disableLeafDecay = val;
	}

	@Override
	public boolean hasLaunchpadPressurePlates() {
		return launchpadPressurePlates;
	}

	@Override
	public void launchpadPressurePlates(boolean val) {
		launchpadPressurePlates = val;
	}

	@Override
	public boolean enableBlockBreak() {
		return enableBlockBreak;
	}

	@Override
	public void enableBlockBreak(boolean val) {
		enableBlockBreak = val;
	}

	@Override
	public boolean enableItemPickup() {
		return enableItemPickup;
	}

	@Override
	public void enableItemPickup(boolean val) {
		enableItemPickup = val;
	}

	@Override
	public boolean enableItemDrop() {
		return enableItemDrop;
	}

	@Override
	public void enableItemDrop(boolean val) {
		enableItemDrop = val;
	}

	@Override
	public boolean enableFoodChange() {
		return enableFoodChange;
	}

	@Override
	public void enableFoodChange(boolean val) {
		enableFoodChange = val;
	}

	@Override
	public boolean hasExplosionFireworks() {
		return explosionFireworks;
	}

	@Override
	public void explosionFireworks(boolean val) {
		explosionFireworks = val;
	}

	@Override
	public boolean enableFallDamage() {
		return enableFallDamage;
	}

	@Override
	public void enableFallDamage(boolean val) {
		enableFallDamage = val;
	}

	@Override
	public boolean isMaintenanceModeEnabled() {
		return maintenanceMode;
	}

	@Override
	public void setMaintenanceMode(boolean val) {
		maintenanceMode = val;
	}

	@Override
	public String maintenanceModeKickMessage() {
		return maintenanceModeKickMessage;
	}

	@Override
	public void maintenanceModeKickMessage(String msg) {
		maintenanceModeKickMessage = msg;
	}

	@Override
	public String maintenanceModeMotd() {
		return maintenanceModeMotd;
	}

	@Override
	public void maintenanceModeMotd(String msg) {
		maintenanceModeMotd = msg;
	}

	@Override
	public boolean enableStackTraceEvent() {
		return stackTraceEvent;
	}

	@Override
	public void enableStackTraceEvent(boolean val) {
		stackTraceEvent = val;
	}

	@Override
	public boolean enableStackTraceBook() {
		return stackTraceBook;
	}

	@Override
	public void enableStackTraceBook(boolean val) {
		stackTraceBook = val;
	}

	@Override
	public boolean enableStackTraceChat() {
		return stackTraceChat;
	}

	@Override
	public void enableStackTraceChat(boolean val) {
		stackTraceChat = val;
	}

	@Override
	public boolean enableWarpsMenu() {
		return enableWarpsMenu;
	}

	@Override
	public void enableWarpsMenu(boolean val) {
		enableWarpsMenu = val;
	}
}
