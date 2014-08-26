package com.caved_in.commons.bans;

import java.util.UUID;

/**
 * @author Brandon Curtis
 * @since 1.0
 */
public interface IPunishment {
	/**
	 * @return type of the punishment
	 * @see com.caved_in.commons.bans.PunishmentType
	 */
	public PunishmentType getPunishmentType();

	/**
	 * @return when the punishment will expire
	 */
	public long getExpiryTime();

	/**
	 * @return when the punishment was issued
	 */
	public long getIssuedTime();

	/**
	 * @return whether or not the punishment is active (If it's expired or not)
	 */
	public boolean isActive();

	/**
	 * @return reason for the punishment being issued
	 */
	public String getReason();

	/**
	 * @return name of the person who issue the punishment
	 */
	public UUID getIssuer();


}
