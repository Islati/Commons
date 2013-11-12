package com.caved_in.commons.config;

import org.simpleframework.xml.Element;

public class Configuration
{
	@Element(name = "DatabaseConfig", type = SqlConfiguration.class)
	private SqlConfiguration sqlConfig;

	@Element(name = "WorldConfig", type = WorldConfiguration.class)
	private WorldConfiguration worldConfig;

	@Element(name = "MaintenanceConfig", type = MaintenanceConfiguration.class)
	private MaintenanceConfiguration maintenanceConfig;
	
	@Element(name = "Item_Menu_Config", type = ItemMenuConfiguration.class)
	private ItemMenuConfiguration itemMenuConfig;

	@Element(name = "Server_Name")
	private String serverName = "EDIT THIS";

	public Configuration(@Element(name = "WorldConfig", type = WorldConfiguration.class) WorldConfiguration worldConfig,
			@Element(name = "DatabaseConfig", type = SqlConfiguration.class) SqlConfiguration sqlConfig,
			@Element(name = "MaintenanceConfig", type = MaintenanceConfiguration.class) MaintenanceConfiguration maintenanceConfig,
			@Element(name = "Item_Menu_Config", type = ItemMenuConfiguration.class)ItemMenuConfiguration itemMenuConfig,
			@Element(name = "Server_Name") String serverName)
	{
		this.worldConfig = worldConfig;
		this.sqlConfig = sqlConfig;
		this.maintenanceConfig = maintenanceConfig;
		this.itemMenuConfig = itemMenuConfig;
		this.serverName = serverName;
	}

	public Configuration()
	{
		this.worldConfig = new WorldConfiguration();
		this.sqlConfig = new SqlConfiguration();
		this.maintenanceConfig = new MaintenanceConfiguration();
		this.itemMenuConfig = new ItemMenuConfiguration();
	}

	public MaintenanceConfiguration getMaintenanceConfig()
	{
		return this.maintenanceConfig;
	}

	public WorldConfiguration getWorldConfig()
	{
		return this.worldConfig;
	}
	
	public ItemMenuConfiguration getItemMenuConfig()
	{
		return this.itemMenuConfig;
	}

	public SqlConfiguration getSqlConfig()
	{
		return this.sqlConfig;
	}

	public String getServerName()
	{
		return this.serverName;
	}
}
