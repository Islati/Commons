package com.caved_in.commons.player;

import lombok.Getter;
import org.bukkit.entity.Player;

@Deprecated
public class SearchedPlayer {
	@Getter
	private boolean online = false;
	@Getter
	private String playerName = "";

	public SearchedPlayer(String playerName, boolean online) {
		this.online = online;
		this.playerName = playerName;
	}

	public Player getPlayer() {
		return Players.getPlayer(playerName);
	}
}
