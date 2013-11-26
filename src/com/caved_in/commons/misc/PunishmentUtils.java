package com.caved_in.commons.misc;

import com.caved_in.commons.data.bans.*;

public class PunishmentUtils {

	public static PunishmentType getType(String Input) {
		switch (Input.toLowerCase()) {
			case "ban":
				return PunishmentType.Ban;
			case "warning":
				return PunishmentType.Warning;
			case "mute":
				return PunishmentType.Mute;
			default:
				break;
		}
		return null;
	}

	public static Punishment getPunishment(String Type, long Expiry, long IssuedTime, boolean Active, String Reason, String Issuer) {
		PunishmentType pType = PunishmentUtils.getType(Type);
		switch (pType) {
			case Warning:
				return (new Warn(Expiry, IssuedTime, Active, Reason, Issuer));
			case Ban:
				return (new Ban(Expiry, IssuedTime, Active, Reason, Issuer));
			case Mute:
				return (new Mute(Expiry, IssuedTime, Active, Reason, Issuer));
			default:
				return null;
		}
	}
}
