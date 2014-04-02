package com.caved_in.commons.config;

import org.simpleframework.xml.Element;

public class Configuration {

	@Element(name = "Database_Backend")
	private boolean sqlBackend = false;

	@Element(name = "DatabaseConfig", type = SqlConfiguration.class)
	private SqlConfiguration sqlConfig;

	@Element(name = "Register_Commands")
	private boolean registerCommands = true;

	@Element(name = "PremiumConfig", type = PremiumConfiguration.class)
	private PremiumConfiguration premiumConfig;

	@Element(name = "WorldConfig", type = WorldConfiguration.class)
	private WorldConfiguration worldConfig;

	@Element(name = "MaintenanceConfig", type = MaintenanceConfiguration.class)
	private MaintenanceConfiguration maintenanceConfig;

	@Element(name = "Item_Menu_Config", type = ItemMenuConfiguration.class)
	private ItemMenuConfiguration itemMenuConfig;

	@Element(name = "Server_Name")
	private String serverName = "EDIT THIS";

	@Element(name = "Enable_NPC")
	private boolean enableNPC = true;

	public Configuration(@Element(name = "WorldConfig", type = WorldConfiguration.class) WorldConfiguration worldConfig,
						 @Element(name = "DatabaseConfig", type = SqlConfiguration.class) SqlConfiguration sqlConfig,
						 @Element(name = "MaintenanceConfig", type = MaintenanceConfiguration.class) MaintenanceConfiguration maintenanceConfig,
						 @Element(name = "Item_Menu_Config", type = ItemMenuConfiguration.class) ItemMenuConfiguration itemMenuConfig,
						 @Element(name = "Enable_NPC") boolean enableNPC,
						 @Element(name = "Server_Name") String serverName,
						 @Element(name = "PremiumConfig", type = PremiumConfiguration.class) PremiumConfiguration premiumConfig,
						 @Element(name = "Database_Backend")boolean sqlBackend,
						 @Element(name = "Register_Commands")boolean registerCommands) {
		this.worldConfig = worldConfig;
		this.sqlConfig = sqlConfig;
		this.maintenanceConfig = maintenanceConfig;
		this.itemMenuConfig = itemMenuConfig;
		this.serverName = serverName;
		this.enableNPC = enableNPC;
		this.premiumConfig = new PremiumConfiguration();
		this.sqlBackend = sqlBackend;
		this.registerCommands = registerCommands;
	}

	public Configuration() {
		this.worldConfig = new WorldConfiguration();
		this.sqlConfig = new SqlConfiguration();
		this.maintenanceConfig = new MaintenanceConfiguration();
		this.itemMenuConfig = new ItemMenuConfiguration();
		this.premiumConfig = new PremiumConfiguration();
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

	public boolean getNPCSEnabled() {
		return enableNPC;
	}

	public boolean hasSqlBackend() {
		return sqlBackend;
	}

	public boolean registerCommands() {
		return registerCommands;
	}
}
