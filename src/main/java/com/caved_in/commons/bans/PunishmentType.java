package com.caved_in.commons.bans;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PunishmentType {
	BAN("ban"),
	MUTE("mute"),
	WARNING("warning"),
	FLAG("flag");

	private static Map<String, PunishmentType> punishments = new HashMap<String, PunishmentType>();

	static {
		for(PunishmentType punishmentType : EnumSet.allOf(PunishmentType.class)) {
			for(String identifier : punishmentType.getIdentifiers()) {
				punishments.put(identifier, punishmentType);
			}
		}
	}

	private String[] identifiers;
	private String identifier;
	PunishmentType(String... identifiers) {
		this.identifiers = identifiers;
		this.identifier = identifiers[0];
	}

	@Override
	public String toString() {
		return identifier;
	}

	public static PunishmentType getPunishmentType(String identifier) {
		return punishments.get(identifier.toLowerCase());
	}

	public String[] getIdentifiers() {
		return identifiers;
	}
}
