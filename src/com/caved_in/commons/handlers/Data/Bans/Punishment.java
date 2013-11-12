package com.caved_in.commons.handlers.Data.Bans;

public abstract class Punishment implements IPunishment
{
	private PunishmentType Type;
	private long ExpiryTime;
	private long IssuedTime;
	private boolean Active = false;
	private String Reason = "";
	private String Issuer = "";

	public Punishment(PunishmentType Type, long Expiry, long Issued, boolean Active, String Reason, String Issuer)
	{
		this.Type = Type;
		this.ExpiryTime = Expiry;
		this.IssuedTime = Issued;
		this.Active = Active;
		this.Reason = Reason;
		this.Issuer = Issuer;
	}

	@Override
	public PunishmentType getType()
	{
		return this.Type;
	}

	@Override
	public long getExpiryTime()
	{
		return this.ExpiryTime;
	}

	@Override
	public long getIssuedTime()
	{
		return this.IssuedTime;
	}

	@Override
	public boolean isActive()
	{
		return this.Active;
	}

	@Override
	public String getReason()
	{
		return this.Reason;
	}

	@Override
	public String getIssuer()
	{
		return this.Issuer;
	}
}
