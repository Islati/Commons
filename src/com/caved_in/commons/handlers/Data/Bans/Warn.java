package com.caved_in.commons.handlers.Data.Bans;

public class Warn extends Punishment
{

	/**
	 * 
	 * @param Expiry
	 * @param Issued
	 * @param Active
	 * @param Reason
	 * @param Issuer
	 */
	public Warn(long Expiry, long Issued, boolean Active, String Reason, String Issuer)
	{
		super(PunishmentType.Warning, Expiry, Issued, Active, Reason, Issuer);
	}

}
