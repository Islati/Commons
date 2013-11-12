package com.caved_in.commons.handlers.Data.Bans;

public abstract interface IPunishment
{
	public PunishmentType getType();

	public long getExpiryTime();

	public long getIssuedTime();

	public boolean isActive();

	public String getReason();

	public String getIssuer();

}
