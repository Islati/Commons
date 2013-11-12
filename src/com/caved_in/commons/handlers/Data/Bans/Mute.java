package com.caved_in.commons.handlers.Data.Bans;

public class Mute extends Punishment
{
	/**
	 * 
	 * @param Expiry
	 * @param Issued
	 * @param Active
	 * @param Reason
	 * @param Issuer
	 */
	public Mute(long Expiry, long Issued, boolean Active, String Reason, String Issuer)
	{
		super(PunishmentType.Mute, Expiry, Issued, Active, Reason, Issuer);
	}
}
