package com.caved_in.commons.handlers.data.bans;

public class Flag extends Punishment
{

	public Flag(long Expiry, long Issued, boolean Active, String Reason, String Issuer)
	{
		super(PunishmentType.Flag, Expiry, Issued, Active, Reason, Issuer);
	}

}
