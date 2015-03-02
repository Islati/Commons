package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.Commons;
import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.player.Players;

import java.util.UUID;
import java.util.concurrent.Callable;

public class MutePlayerCallable implements Callable<Boolean> {

    private UUID id = null;
    private String name;

    private Punishment punishment;

    public MutePlayerCallable(String name, Punishment punishment) {
        this.name = name;
        this.punishment = punishment;
    }

    public MutePlayerCallable(UUID id, Punishment punishment) {
        this.id = id;
        this.punishment = punishment;
    }

    @Override
    public Boolean call() throws Exception {

        if (id == null) {
            id = Players.getUUIDFromName(name);
        }
        return Commons.getInstance().getServerDatabase().insertPlayerPunishment(punishment, id);
    }
}
