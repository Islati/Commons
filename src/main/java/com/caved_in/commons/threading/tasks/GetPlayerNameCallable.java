package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.player.Players;

import java.util.UUID;
import java.util.concurrent.Callable;

public class GetPlayerNameCallable implements Callable<String> {
    private UUID uniqueId;

    public GetPlayerNameCallable(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public String call() throws Exception {
        return Players.getNameFromUUID(uniqueId);
    }
}
