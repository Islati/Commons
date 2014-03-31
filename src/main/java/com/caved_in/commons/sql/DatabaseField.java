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
	//Fields related to the player table
	PLAYER_UNIQUE_ID("player_id"),
	PLAYER_NAME("player_name"),
	PLAYER_NAME_PREFIX("player_prefix"),
	PLAYER_IP_ADDRESS("player_ip"),
	//Fields related to the premium table
	PREMIUM_STATUS("premium"),

	// Online status field for the online-status
	ONLINE_STATUS("online"),
	//Fields related to the shop table(s)
	SHOP_ITEM_ID("item_id"),
	SHOP_ITEM_NAME("item_name"),
	SHOP_TIME_PURCHASED("time_purchased"),
	SHOP_ITEM_PRICE("item_price"),
	SHOP_ITEM_DESCRIPTION("item_desc"),
	SHOP_ITEM_PURCHASE_TIME("TIME_PURCHASED"),
	SHOP_ITEM_EXPIRE_TIME("TIME_EXPIRE"),
	//Fields relating to the server table
	SERVER_ID("svr_id"),
	SERVER_NAME("svr_name"),
	SERVER_IP("svr_ip"),
	SERVER_PLAYER_LIMIT("svr_player_limit"),
	//Punishments and related table data
	PUNISHMENT_ID("PUN_ID"),
	PUNISHMENT_ISSUER_UID("PUN_ISSUER_UID"),
	PUNISHMENT_ISSUED_TIME("PUN_ISSUED"),
	PUNISHMENT_EXPIRATION_TIME("PUN_EXPIRE"),
	PUNISHMENT_REASON("PUN_REASON"),
	//Friends Table
	FRIEND_USER_ID



	private String columnName;

	DatabaseField(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public String toString() {
		return columnName;
	}
}