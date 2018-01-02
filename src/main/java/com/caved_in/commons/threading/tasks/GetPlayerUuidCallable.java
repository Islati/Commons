package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.player.Players;

import java.util.UUID;
import java.util.concurrent.Callable;

public class GetPlayerUuidCallable implements Callable<UUID> {
    private String playerName;

    public GetPlayerUuidCallable(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public UUID call() throws Exception {
        return Players.getUUIDFromName(playerName);
    }
}
