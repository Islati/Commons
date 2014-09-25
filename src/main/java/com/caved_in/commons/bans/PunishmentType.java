package com.caved_in.commons.bans;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brandon Curtis
 * @since 1.0
 */
public enum PunishmentType {
	WARNING("warning", 0),
	INFRACTION("infraction", 1),
	MUTE("mute", 2),
	FLAG("flag", 3),
	BAN("ban", 4);

	private static Map<String, PunishmentType> punishments = new HashMap<>();
	private static Map<Integer, PunishmentType> punishmentIds = new HashMap<>();

	static {
		for (PunishmentType punishmentType : EnumSet.allOf(PunishmentType.class)) {
			punishmentIds.put(punishmentType.getId(), punishmentType);
			punishments.put(punishmentType.toString(), punishmentType);
		}
	}

	private String identifier;
	private int id;

	PunishmentType(String identifier, int id) {
		this.identifier = identifier;
		this.id = id;
	}

	@Override
	public String toString() {
		return identifier;
	}

	public int getId() {
		return id;
	}

	public static PunishmentType getPunishmentType(String identifier) {
		return punishments.get(identifier.toLowerCase());
	}

	public static PunishmentType getPunishmentType(int id) {
		return punishmentIds.get(id);
	}
}
