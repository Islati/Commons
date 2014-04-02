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
	PLAYER_TABLE("server_players"),
	PLAYER_UNIQUE_ID("player_id"),
	PLAYER_NAME("player_name"),
	PLAYER_IP_ADDRESS("player_ip"),
	PLAYER_CURRENCY("player_money"),
	//Prefixes Table
	PLAYER_PREFIX_TABLE("server_prefixes"),
	PLAYER_NAME_PREFIX("player_prefix"),
	//Fields related to the premium table
	PREMIUM_TABLE("server_vipusers"),
	PREMIUM_STATUS("premium"),
	// Online status field for the online-status
	ONLINE_TABLE("server_online"),
	ONLINE_STATUS("online"),
	//Fields related to the shop table(s)
	SHOP_PURCHASES_TABLE("server_shophistory"),
	SHOP_ITEMS_TABLE("server_shopitems"),
	SHOP_ITEM_ID("item_id"),
	SHOP_ITEM_NAME("item_name"),
	SHOP_TIME_PURCHASED("time_purchased"),
	SHOP_ITEM_PRICE("item_price"),
	SHOP_ITEM_DESCRIPTION("item_desc"),
	SHOP_ITEM_PURCHASE_TIME("shop_soldtime"),
	SHOP_ITEM_EXPIRE_TIME("shop_expiretime"),
	//Fields relating to the server table
	SERVER_ID("svr_id"),
	SERVER_NAME("svr_name"),
	SERVER_IP("svr_ip"),
	SERVER_PLAYER_LIMIT("svr_player_limit"),
	//Server connections table
	SERVER_CONNECTION_TABLE("server_connections"),
	SERVER_CONNECT_ID("connect_id"),
	SERVER_CONNECT_TIME("connect_time"),
	//Punishments and related table data
	PUNISHMENTS_TABLE("server_punishments"),
	PUNISHMENT_ID("pun_id"),
	PUNISHMENT_ISSUER_UID("pun_giver_id"),
	PUNISHMENT_ISSUED_TIME("pun_issued"),
	PUNISHMENT_EXPIRATION_TIME("pun_expires"),
	PUNISHMENT_REASON("pun_reason"),
	PUNISHMENT_NAME("pun_name"),
	//Disguises table
	DISGUISE_TABLE("server_disguises"),
	DIGUISE_AS("diguise_as"),
	DISGUISE_TIME("disguise_time"),
	DISGUISE_ACTIVE("disguise_active"),
	//Friends Table
	FRIENDS_TABLE("server_friends"),
	FRIEND_USER_ID("friend_id"),
	FRIEND_ACCEPTED("friend_accepted");

	private String columnName;

	DatabaseField(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public String toString() {
		return columnName;
	}
}