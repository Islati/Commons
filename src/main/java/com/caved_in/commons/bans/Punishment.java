package com.caved_in.commons.bans;

public class Punishment implements IPunishment {
	private PunishmentType punishmentType;
	private long expiryTime;
	private long issuedTime;
	private boolean active = false;
	private String reason = "";
	private String issuer = "";

	public Punishment(PunishmentType punishmentType, long expiry, long issued, boolean isActive, String reason, String issuer) {
		this.punishmentType = punishmentType;
		this.expiryTime = expiry;
		this.issuedTime = issued;
		this.active = isActive;
		this.reason = reason;
		this.issuer = issuer;
	}

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
