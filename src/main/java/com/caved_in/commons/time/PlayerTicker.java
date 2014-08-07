package com.caved_in.commons.time;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerTicker extends BasicTicker {

	private Map<UUID, Integer> calls = new HashMap<>();

	public PlayerTicker(int allowAmount) {
		super(allowAmount);
	}

	private int getAmount(Player player) {
		UUID id = player.getUniqueId();
		if (!calls.containsKey(id)) {
			return 0;
		}

		return calls.get(id);
	}

	private void setAmount(Player player, int amount) {
		calls.put(player.getUniqueId(), amount);
	}

	public void tick(Player player) {
		setAmount(player, getAmount(player) + 1);
	}

	public boolean allow(Player player) {
		if (getAmount(player) >= allowAmount()) {
			reset(player);
			return true;
		}

		return false;
	}

	public void reset(Player player) {
		calls.put(player.getUniqueId(), 0);
	}
}
