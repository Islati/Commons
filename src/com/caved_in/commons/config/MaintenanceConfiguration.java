package com.caved_in.commons.config;

import com.caved_in.commons.handlers.Utilities.StringUtil;

import org.simpleframework.xml.Element;

public class MaintenanceConfiguration
{
	@Element(name = "maintenance_motd")
	private String maintenanceModeMOTD = "&aThis server is currently undergoing maintenance";

	@Element(name = "maintenance_kick_message")
	private String maintenanceKickMessage = "&cThis server is currently undergoing maintenance; Sorry for the inconvienance";

	private boolean maintenanceMode = false;

	public MaintenanceConfiguration(@Element(name = "maintenance_motd") String maintenanceMOTD, @Element(name = "maintenance_kick_message") String maintenanceKickMessage)
	{
		this.maintenanceModeMOTD = maintenanceMOTD;
		this.maintenanceKickMessage = maintenanceKickMessage;
	}

	public MaintenanceConfiguration()
	{

	}

	public boolean isMaintenanceMode()
	{
		return this.maintenanceMode;
	}

	public void setMaintenanceMode(boolean maintenance)
	{
		this.maintenanceMode = maintenance;
	}

	public void toggleMaintenance()
	{
		this.maintenanceMode = !this.maintenanceMode;
	}

	public String getMotd()
	{
		return StringUtil.formatColorCodes(this.maintenanceModeMOTD);
	}

	public void setMotd(String message)
	{
		this.maintenanceModeMOTD = message;
	}

	public String getKickMessage()
	{
		return StringUtil.formatColorCodes(this.maintenanceKickMessage);
	}

	public void setKickMessage(String message)
	{
		this.maintenanceKickMessage = message;
	}

}
