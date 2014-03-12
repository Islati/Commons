package com.caved_in.commons.packets.protocol;

import com.caved_in.commons.reflection.ReflectionUtilities;

public enum Protocol {

	HANDSHAKE,
	PLAY,
	STATUS,
	LOGIN;

	private Protocol() {
	}

	public Object toVanilla() {
		switch (this) {
			case HANDSHAKE:
				return Enum.valueOf(ReflectionUtilities.getNMSClass("EnumProtocol"), "HANDSHAKING");
			case PLAY:
				return Enum.valueOf(ReflectionUtilities.getNMSClass("EnumProtocol"), "PLAY");
			case STATUS:
				return Enum.valueOf(ReflectionUtilities.getNMSClass("EnumProtocol"), "STATUS");
			case LOGIN:
				return Enum.valueOf(ReflectionUtilities.getNMSClass("EnumProtocol"), "LOGIN");
			default:
				return null;
		}
	}
}
