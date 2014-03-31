package com.caved_in.commons.sql;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public enum DatabaseField {
	/* Fields related for to the player table */
	PLAYER_UNIQUE_ID("PL_UID"),
	PLAYER_NAME("PL_NAME"),
	PLAYER_NAME_PREFIX("PREFIX"),
	PLAYER_IP_ADDRESS("PL_IP"),
	/* Online status field for the online-status */
	ONLINE("ONLINE"),
	/* Fields related to the shop table(s) */
	SHOP_ITEM_ID("ITEM_ID"),
	SHOP_ITEM_NAME("ITEM_NAME"),
	SHOP_TIME_PURCHASED("TIME_PURCHASE"),
	SHOP_ITEM_PRICE("ITEM_PRICE"),
	SHOP_ITEM_DESCRIPTION("ITEM_DESCRIPTION"),
	SHOP_ITEM_PURCHASE_TIME("")

	SERVER_ID("SVR_ID"),


	}
