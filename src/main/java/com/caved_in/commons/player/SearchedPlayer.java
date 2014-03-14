package com.caved_in.commons.player;

import org.bukkit.entity.Player;

@Deprecated
public class SearchedPlayer {
	private boolean online = false;
	private String playerName = "";

	public SearchedPlayer(String playerName, boolean online) {
		this.online = online;
		this.playerName = playerName;
	}

	public Player getPlayer() {
		return Players.getPlayer(playerName);
	}

	public boolean isOnline() {
		return online;
	}

	public String getPlayerName() {
		return playerName;
	}
}
