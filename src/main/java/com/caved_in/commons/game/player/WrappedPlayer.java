package com.caved_in.commons.game.player;

import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WrappedPlayer implements PlayerWrapper {
	private UUID id;
	private String name;

	public WrappedPlayer(Player player) {
		id = player.getUniqueId();
		name = player.getName();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public UUID getId() {
		return id;
	}

	public Player getPlayer() {
		return Players.getPlayer(id);
	}
}
