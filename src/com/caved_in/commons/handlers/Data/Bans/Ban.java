package com.caved_in.commons.handlers.Data.Bans;

public class Ban extends Punishment
{

	/**
	 * 
	 * @param Expiry
	 * @param Issued
	 * @param Active
	 * @param Reason
	 * @param Issuer
	 */
	public Ban(long Expiry, long Issued, boolean Active, String Reason, String Issuer)
	{
		super(PunishmentType.Ban, Expiry, Issued, Active, Reason, Issuer);
	}

}
