package com.caved_in.commons.game.thread;

import com.caved_in.commons.game.GameCore;

public class GameUpdateThread implements Runnable {

    private GameCore core;

    public GameUpdateThread(com.caved_in.commons.game.CraftGame core) {
        this.core = core;
    }

    @Override
    public void run() {
        core.update();
    }
}
