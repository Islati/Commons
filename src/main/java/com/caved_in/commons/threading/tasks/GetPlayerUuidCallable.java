package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.player.Players;

import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
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
