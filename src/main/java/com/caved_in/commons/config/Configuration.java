package com.caved_in.commons.config;

import org.simpleframework.xml.Element;

public class Configuration {

	@Element(name = "mysql-backend")
	private boolean sqlBackend = false;

	@Element(name = "database-config", type = SqlConfiguration.class)
	private SqlConfiguration sqlConfig;

	@Element(name = "register-commands")
	private boolean registerCommands = true;

	@Element(name = "premium-config", type = PremiumConfiguration.class)
	private PremiumConfiguration premiumConfig;

	@Element(name = "world-config", type = WorldConfiguration.class)
	private WorldConfiguration worldConfig;

	@Element(name = "maintenance-config", type = MaintenanceConfiguration.class)
	private MaintenanceConfiguration maintenanceConfig;

	@Element(name = "item-menu-config", type = ItemMenuConfiguration.class)
	private ItemMenuConfiguration itemMenuConfig;

	@Element(name = "debug-config", type = DebugConfig.class)
	private DebugConfig debugConfig;

	@Element(name = "server-name")
	private String serverName = "EDIT THIS";

	public Configuration(@Element(name = "world-config", type = WorldConfiguration.class) WorldConfiguration worldConfig,
						 @Element(name = "database-config", type = SqlConfiguration.class) SqlConfiguration sqlConfig,
						 @Element(name = "maintenance-config", type = MaintenanceConfiguration.class) MaintenanceConfiguration maintenanceConfig,
						 @Element(name = "item-menu-config", type = ItemMenuConfiguration.class) ItemMenuConfiguration itemMenuConfig,
						 @Element(name = "server-name") String serverName,
						 @Element(name = "premium-config", type = PremiumConfiguration.class) PremiumConfiguration premiumConfig,
						 @Element(name = "mysql-backend") boolean sqlBackend,
						 @Element(name = "register-commands") boolean registerCommands,
						 @Element(name = "debug-config", type = DebugConfig.class) DebugConfig debugConfig) {
		this.worldConfig = worldConfig;
		this.sqlConfig = sqlConfig;
		this.maintenanceConfig = maintenanceConfig;
		this.itemMenuConfig = itemMenuConfig;
		this.serverName = serverName;
		this.premiumConfig = premiumConfig;
		this.sqlBackend = sqlBackend;
		this.registerCommands = registerCommands;
		this.debugConfig = debugConfig;
	}

	public Configuration() {
		this.worldConfig = new WorldConfiguration();
		this.sqlConfig = new SqlConfiguration();
		this.maintenanceConfig = new MaintenanceConfiguration();
		this.itemMenuConfig = new ItemMenuConfiguration();
		this.premiumConfig = new PremiumConfiguration();
		this.debugConfig = new DebugConfig();
	}

	public MaintenanceConfiguration getMaintenanceConfig() {
		return maintenanceConfig;
	}

	public WorldConfiguration getWorldConfig() {
		return worldConfig;
	}

	public ItemMenuConfiguration getItemMenuConfig() {
		return itemMenuConfig;
	}

	public SqlConfiguration getSqlConfig() {
		return sqlConfig;
	}

	public String getServerName() {
		return serverName;
	}

	public PremiumConfiguration getPremiumConfig() {
		return premiumConfig;
	}

	public boolean hasSqlBackend() {
		return sqlBackend;
	}

	public boolean registerCommands() {
		return registerCommands;
	}

	public DebugConfig getDebugConfig() {
		return debugConfig;
	}
}
