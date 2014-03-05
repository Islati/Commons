package com.caved_in.commons.npc;

public enum SlotType {

	ITEM_IN_HAND,
	HELMET,
	BODY_ARMOR,
	PANTS,
	SHOES;

	private SlotType() {
	}

	public int toNumeric() {
		switch (this) {
			case ITEM_IN_HAND:
				return 0;
			case HELMET:
				return 1;
			case BODY_ARMOR:
				return 2;
			case PANTS:
				return 3;
			case SHOES:
				return 4;
			default:
				return -1;
		}
	}
}
