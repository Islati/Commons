package com.caved_in.commons.bans;

import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;

import java.util.UUID;

public class PunishmentBuilder {
	private PunishmentType type;
	private long expiry;
	private long issued;
	private String reason;
	private UUID issuer;

	public PunishmentBuilder() {
	}

	public PunishmentBuilder withType(PunishmentType type) {
		this.type = type;
		return this;
	}

	public PunishmentBuilder expiresOn(long expiry) {
		this.expiry = expiry;
		return this;
	}

	public PunishmentBuilder issuedOn(long issued) {
		this.issued = issued;
		return this;
	}

	public PunishmentBuilder withReason(String reason) {
		this.reason = reason;
		return this;
	}

	public PunishmentBuilder withIssuer(UUID issuer) {
		this.issuer = issuer;
		return this;
	}

	public PunishmentBuilder permanent(boolean val) {
		if (val) {
			return expiresOn(System.currentTimeMillis() + TimeHandler.getTimeInMilles(10, TimeType.YEAR));
		}
		return this;
	}

	public Punishment build() {
		Punishment punishment = new Punishment();
		long timestamp = System.currentTimeMillis();
		punishment.setActive(timestamp > expiry);
		punishment.setExpiryTime(expiry);
		punishment.setIssuedTime(issued);
		punishment.setIssuer(issuer);
		punishment.setPunishmentType(type);
		punishment.setReason(reason);
		return punishment;
	}
}
