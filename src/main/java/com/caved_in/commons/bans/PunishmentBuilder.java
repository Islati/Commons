package com.caved_in.commons.bans;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
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
