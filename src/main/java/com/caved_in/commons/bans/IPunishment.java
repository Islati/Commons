package com.caved_in.commons.bans;

public abstract interface IPunishment {
	public PunishmentType getPunishmentType();

	public long getExpiryTime();

	public long getIssuedTime();

	public boolean isActive();

	public String getReason();

	public String getIssuer();

}
