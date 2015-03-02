package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.Commons;
import com.caved_in.commons.player.Players;

import java.util.UUID;
import java.util.concurrent.Callable;

public class GivePlayerTunnelsExpCallable implements Callable<Boolean> {

    private String playerName;
    private UUID playerId = null;

    private int amount = 0;

    public GivePlayerTunnelsExpCallable(String name, int amount) {
        this.playerName = name;
        this.amount = amount;
    }

    public GivePlayerTunnelsExpCallable(UUID id, int amount) {
        this.playerId = id;
        this.amount = amount;
    }

    @Override
    public Boolean call() throws Exception {
        boolean updated = false;

        if (playerId == null) {
            playerId = Players.getUUIDFromName(playerName);
        }
        updated = Commons.getInstance().getServerDatabase().givePlayerMoney(playerId, amount);
        return updated;
    }
}
