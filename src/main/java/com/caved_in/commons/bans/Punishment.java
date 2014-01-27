package com.caved_in.commons.bans;

/**
 * @author Brandon Curtis
 * @see com.caved_in.commons.bans.IPunishment
 * @since 1.0
 * @version 1.0
 */
public class Punishment implements IPunishment {
	private PunishmentType punishmentType;
	private long expiryTime;
	private long issuedTime;
	private boolean active;
	private String reason;
	private String issuer;

	/**
	 * Constructs a new punishment object
	 * @param punishmentType type of the punishment being issued
	 * @param expiry when the punishment will expire
	 * @param issued when the punishment was issued
	 * @param isActive whether or not the punishment is active
	 * @param reason the reason for the punishment being issued
	 * @param issuer the person / name of who issued the punishment
	 */
	public Punishment(PunishmentType punishmentType, long expiry, long issued, boolean isActive, String reason, String issuer) {
		this.punishmentType = punishmentType;
		this.expiryTime = expiry;
		this.issuedTime = issued;
		this.active = isActive;
		this.reason = reason;
		this.issuer = issuer;
	}

	/**
	 * Constructs a new punishment object based on the punishmenttype inputted via string
	 * @param type type of the punishment being issued
	 * @param expiry when the punishment will expire
	 * @param issued when the punishment was issued
	 * @param isActive whether or not the punishment is active
	 * @param reason the reason for the punishment being issued
	 * @param issuer the person / name of who issued the punishment
	 */
	public Punishment(String type, long expiry, long issued, boolean isActive, String reason, String issuer) {
		this(PunishmentType.getPunishmentType(type), expiry, issued, isActive, reason, issuer);
	}

	@Override
	public PunishmentType getPunishmentType() {
		return this.punishmentType;
	}

	@Override
	public long getExpiryTime() {
		return this.expiryTime;
	}

	@Override
	public long getIssuedTime() {
		return this.issuedTime;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	@Override
	public String getReason() {
		return this.reason;
	}

	@Override
	public String getIssuer() {
		return this.issuer;
	}
}
