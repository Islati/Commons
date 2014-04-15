package com.caved_in.commons.bans;

import java.util.UUID;

/**
 * @author Brandon Curtis
 * @version 1.0
 * @see com.caved_in.commons.bans.IPunishment
 * @since 1.0
 */
public class Punishment implements IPunishment {
	private PunishmentType punishmentType;
	private long expiryTime;
	private long issuedTime;
	private boolean active;
	private String reason;
	private UUID issuer;

	public Punishment() {
	}

	/**
	 * Constructs a new punishment object
	 *
	 * @param punishmentType type of the punishment being issued
	 * @param expiry         when the punishment will expire
	 * @param issued         when the punishment was issued
	 * @param isActive       whether or not the punishment is active
	 * @param reason         the reason for the punishment being issued
	 * @param issuer         the person / name of who issued the punishment
	 */
	public Punishment(PunishmentType punishmentType, long expiry, long issued, boolean isActive, String reason, UUID issuer) {
		this.punishmentType = punishmentType;
		this.expiryTime = expiry;
		this.issuedTime = issued;
		this.active = isActive;
		this.reason = reason;
		this.issuer = issuer;
	}

	/**
	 * Constructs a new punishment object based on the punishmenttype inputted via string
	 *
	 * @param type     type of the punishment being issued
	 * @param expiry   when the punishment will expire
	 * @param issued   when the punishment was issued
	 * @param isActive whether or not the punishment is active
	 * @param reason   the reason for the punishment being issued
	 * @param issuer   the person / name of who issued the punishment
	 */
	public Punishment(String type, long expiry, long issued, boolean isActive, String reason, UUID issuer) {
		this(PunishmentType.getPunishmentType(type), expiry, issued, isActive, reason, issuer);
	}

	@Override
	public PunishmentType getPunishmentType() {
		return punishmentType;
	}

	@Override
	public long getExpiryTime() {
		return expiryTime;
	}

	@Override
	public long getIssuedTime() {
		return issuedTime;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public String getReason() {
		return reason;
	}

	@Override
	public UUID getIssuer() {
		return issuer;
	}

	public void setPunishmentType(PunishmentType punishmentType) {
		this.punishmentType = punishmentType;
	}

	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}

	public void setIssuedTime(long issuedTime) {
		this.issuedTime = issuedTime;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setIssuer(UUID uuid) {
		issuer = uuid;
	}
}
