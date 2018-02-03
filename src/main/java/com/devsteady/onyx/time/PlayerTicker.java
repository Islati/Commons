package com.devsteady.onyx.time;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerTicker extends BasicTicker {

    private Map<UUID, Integer> calls = new HashMap<>();

    public PlayerTicker(int allowAmount) {
        super(allowAmount);
    }

    public int getTickCount(Player player) {
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
        setAmount(player, getTickCount(player) + 1);
    }

    public boolean allow(Player player) {
        if (getTickCount(player) >= allowAmount()) {
            reset(player);
            return true;
        }

        return false;
    }

    public void reset(Player player) {
        calls.put(player.getUniqueId(), 0);
    }

    public void clear(Player player) {
        calls.remove(player.getUniqueId());
    }
}
