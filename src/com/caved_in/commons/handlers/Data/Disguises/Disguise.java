package com.caved_in.commons.handlers.Data.Disguises;

public class Disguise
{
	private String playerDisguised = "";
	private String disguisedAs = "";
	private String serverOn = "";

	/**
	 * 
	 * @param playerDisguised
	 * @param disguisedAs
	 * @param Server
	 */
	public Disguise(String playerDisguised, String disguisedAs, String Server)
	{
		this.playerDisguised = playerDisguised;
		this.disguisedAs = disguisedAs;
		this.serverOn = Server;
	}

	public String getPlayerDisguised()
	{
		return playerDisguised;
	}

	public String getDisguisedAs()
	{
		return disguisedAs;
	}

	public String getServerOn()
	{
		return serverOn;
	}

}
